package excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class ExcelReader {

    /**
     * The Excel workbook used for this instance of the class.
     */
    private static Workbook workbook;

    /**
     * The sheet to read in the Excel workbook.
     * Currently sets up to read only the first sheet of the Excel workbook.
     */
    private Sheet sheet;

    /**
     * The number of rows that the Excel document uses as the header.
     */
    private int headerRowsCount;

    /**
     * The header for the column containing all names that lookups will be performed against.
     */
    private String nameHeader;

    /**
     * Instantiate the class with a given Excel document and parse the headers for use by this class.
     * @param excelFilepath     The path of the Excel document.
     * @param headerRowsCount   The number of rows in the header of the Excel document.
     */
    public ExcelReader(String excelFilepath, int headerRowsCount) throws FileNotFoundException {
        this.setRelevantWorkbook(excelFilepath);
        sheet = ExcelReader.workbook.getSheetAt(0);
        this.headerRowsCount = headerRowsCount;
        this.nameHeader = "name";
    }

    /**
     * Sets the appropriate Excel document type based on the filename extension given.
     * @param excelFilepath     The path of the Excel document.
     */
    private void setRelevantWorkbook(String excelFilepath) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilepath));

        try {
            if (excelFilepath.endsWith("xls")) {
                ExcelReader.workbook = new HSSFWorkbook(inputStream);
            } else if (excelFilepath.endsWith("xlsx")) {
                ExcelReader.workbook = new XSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException("Incorrect file format");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel document set:");
        System.out.println("Filepath: " + excelFilepath);
    }

    /**
     * Reads the given Excel document and returns it as a string.
     * Note: The problem with this method is that it skips over leading, blank cells
     * @return                  A string representation of the Excel document.
     */
    public String readExcelDocument() {
        final char CELL_SEPARATED_VALUE = '|';

        StringBuilder output = new StringBuilder();
        for (Row currentRow : sheet) {
            output.append(CELL_SEPARATED_VALUE);
            for(int cellIterator = 0; cellIterator < currentRow.getLastCellNum(); cellIterator++) {
                Cell cellContent = currentRow.getCell(cellIterator, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                output.append(this.printCellContent(cellContent));
                output.append(CELL_SEPARATED_VALUE);
            }
            output.append("\n");
        }
        return output.toString().trim();
    }

    /**
     * Get the index of a column given the column header value.
     * @param headerName        The name of the header column to search for.
     * @return                  The column index of the header that was found.
     */
    public int getColumnIndex(String headerName) {
        int docNumColumns = Integer.MIN_VALUE;
        for (int rowIterator = 0; rowIterator < headerRowsCount; rowIterator++) {
            Row currentRow = this.sheet.getRow(rowIterator);

            // Determine the max number of columns in the row headers
            // Currently, this value is not being used
            int lastColumn = Math.max(currentRow.getLastCellNum(), docNumColumns);
            if (lastColumn > docNumColumns) {
                docNumColumns = lastColumn;
            }

            // Iterate through each cell in the row to see if there's a match of the given column name
            for (int cellIterator = 0; cellIterator < lastColumn; cellIterator++) {
                Cell cellContent = currentRow.getCell(cellIterator, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //System.out.println("x,y: " + rowIterator + "," + cellIterator);
                headerName = headerName.trim();
                if (this.printCellContent(cellContent).equalsIgnoreCase(headerName)) {
                    return cellIterator;
                }
            }
        }

        System.out.println("No match found for the header column name.");
        return -1;
    }

    /**
     * Get the list of all names matching a given input string.
     * @param nameQuery          The name to search for within the "name" column.
     * @return              The list of matching names.
     */
    public String[] listMatchingNames(String nameQuery)  throws IllegalArgumentException {
        // Get name column
        int nameColumnIndex = this.getColumnIndex("name");

        // Go through the names column and get all names matching the given parameter.
        // Results are saved in a HashSet so that duplicates are not added to result set.
        HashSet<String> nameList = new HashSet<>();
        for (Row currentRow : this.sheet) {
            Cell cellContent = currentRow.getCell(nameColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cellContent.getStringCellValue().toLowerCase().contains(nameQuery.toLowerCase()))
                nameList.add(cellContent.getStringCellValue().trim());
        }

        return nameList.toArray(new String[nameList.size()]);
    }

    /**
     * Get the row number by providing a name a looking through the column with the header name, "name."
     * Gets only the first matching name.  In order to get the most accurate name, give user list to narrow down from
     * by using the listMatchingNames() method.
     * @param nameLookup    The name to search for within the "name" column.
     * @return              The row index where the name is found in.
     */
    public int getRowIndex(String nameLookup) {
        // Get name column
        int nameColumnIndex = getColumnIndex(nameHeader);

        for (int rowIterator = 0; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
            Row currentRow = sheet.getRow(rowIterator);
            Cell cellContent = currentRow.getCell(nameColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cellContent.getStringCellValue().toLowerCase().contains(nameLookup.toLowerCase()))
                return rowIterator;
        }

        System.out.println("No match found for the name given.");
        return -1;
    }

    /**
     * Perform a single lookup given the row index and the column given the header name.
     * @param rowIndex      The row for getting the desired information.
     * @param headerName    The column name for looking up the desired information.
     * @return              A single result lookup.
     */
    public String lookupDetail(int rowIndex, String headerName) {
        // Establish the row that will be read from
        Row desiredRow = sheet.getRow(rowIndex);

        // Read target field based on the given Excel header name
        int columnNum = getColumnIndex(headerName);
        Cell cellContent = desiredRow.getCell(columnNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return this.printCellContent(cellContent);
    }

    /**
     * Perform a multiple lookups given the row index and the list of columns given their header names.
     * @param rowIndex              The row for getting all the desired information.
     * @param columnHeaderNames     An ordered list of the top-row header names.
     * @return                      An ordered list of lookup results.
     */
    public String[] lookupDetails(int rowIndex, String[] columnHeaderNames) {
        // Establish the row that will be read from
        Row desiredRow = sheet.getRow(rowIndex);

        // Go through each each column matching that of the given list and get the cell content from the given row
        ArrayList<String> desiredInfo = new ArrayList<>();
        for (String headerName : columnHeaderNames) {
            int columnNum = getColumnIndex(headerName);
            Cell cellContent = desiredRow.getCell(columnNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            desiredInfo.add(this.printCellContent(cellContent));
        }
        return desiredInfo.toArray(new String[desiredInfo.size()]);
    }

    /**
     * Read the contents of an Excel cell.
     * @param inputCell             The cell to read
     */
    private String printCellContent(Cell inputCell) {
        String numericToString;

        switch (inputCell.getCellTypeEnum()) {
            case STRING:
                return inputCell.getStringCellValue();
            case NUMERIC:
                numericToString = Double.toString(inputCell.getNumericCellValue());
                numericToString = trimTrailingDecimal(numericToString);
                return numericToString;
            case BOOLEAN:
                return Boolean.toString(inputCell.getBooleanCellValue());
            case FORMULA:
                switch (inputCell.getCachedFormulaResultTypeEnum()) {
                    case STRING:
                        numericToString = inputCell.getStringCellValue();
                        numericToString = trimTrailingDecimal(numericToString);
                        return numericToString;
                    case NUMERIC:
                        numericToString = Double.toString(inputCell.getNumericCellValue());
                        numericToString = trimTrailingDecimal(numericToString);
                        return numericToString;
                }
            default:
                return "";
        }
    }

    /**
     * Remove the trailing ".0" characters at the end of cell contents.
     * @param numericString             The numeric value converted to String
     * @return                          The resulting String
     */
    private String trimTrailingDecimal(String numericString) {
        int stringLength = numericString.length();
        if (stringLength > 2)
            if (numericString.substring(stringLength - 2, stringLength).equalsIgnoreCase(".0"))
                numericString = numericString.substring(0, numericString.length() - 2);
        return numericString;
    }
}