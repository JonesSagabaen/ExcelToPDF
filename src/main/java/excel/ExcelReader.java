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
     * Constructor currently sets up to read only the first sheet of the Excel workbook.
     */
    private Sheet sheet;

    /**
     * Number of rows in the Excel document that are associated with the document header.
     */
    private int numOfHeaderRows;

    /**
     * Instantiate the class with a given Excel document and parse the headers for use by this class.
     * @param excelFilePath     The path of the Excel document.
     * @param numOfHeaderRows   The number of rows in the header of the Excel document.
     */
    public ExcelReader(String excelFilePath, int numOfHeaderRows) throws FileNotFoundException {
        this.setRelevantWorkbook(excelFilePath);
        sheet = ExcelReader.workbook.getSheetAt(0);
        this.numOfHeaderRows = numOfHeaderRows;
    }

    /**
     * Helper method used by the constructors for setting this class' Excel workbook.
     * @param excelFilePath     The path of the Excel document.
     */
    private void setRelevantWorkbook(String excelFilePath) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        try {
            if (excelFilePath.endsWith("xls")) {
                ExcelReader.workbook = new HSSFWorkbook(inputStream);
            } else if (excelFilePath.endsWith("xlsx")) {
                ExcelReader.workbook = new XSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException("Incorrect file format");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Excel document set:");
        System.out.println("Filepath: " + excelFilePath);
    }

    /**
     * Reads the given Excel document and returns it as a string.
     * Note: The problem with this method is that it skips over leading, blank cells
     * @return                  The text of the Excel document associated with this class.
     */
    public String readEntireDocument() {
        final char CELL_SEPARATED_VALUE = '|';

        StringBuilder output = new StringBuilder();
        for (Row currentRow : this.sheet) {
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
        for (int rowIterator = 0; rowIterator < numOfHeaderRows; rowIterator++) {
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
     * Get the list of all names matching the given name to search for.
     * @param name          The name to search for in the Excel workbook.
     * @return              The list of matching names.
     */
    public String[] listMatchingNames(String name)  throws IllegalArgumentException {
        // Get name column
        int nameColumnIndex = this.getColumnIndex("name");

        // Go through the names column and get all names matching the given parameter.
        // Results are saved in a HashSet so that duplicates are not added to result set.
        HashSet<String> nameList = new HashSet<>();
        for (Row currentRow : this.sheet) {
            Cell cellContent = currentRow.getCell(nameColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cellContent.getStringCellValue().toLowerCase().contains(name.toLowerCase()))
                nameList.add(cellContent.getStringCellValue().trim());
        }

        return nameList.toArray(new String[nameList.size()]);
    }

    /**
     * Gets the row number of the given name.
     * Gets only the first matching name.  In order to get the most accurate name, give user list to narrow down from
     * by using the listMatchingNames() method.
     * @param nameLookup    The name to search within the Excel name column.
     * @return              The row number where the name is found in.
     */
    public int getRowIndex(String nameLookup) {
        // Get name column
        int nameColumnIndex = this.getColumnIndex("name");

        for (int rowIterator = 0; rowIterator <= sheet.getLastRowNum(); rowIterator++) {
            Row currentRow = this.sheet.getRow(rowIterator);
            Cell cellContent = currentRow.getCell(nameColumnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cellContent.getStringCellValue().toLowerCase().contains(nameLookup.toLowerCase()))
                return rowIterator;
        }

        System.out.println("No match found for the name given.");
        return -1;
    }

    /**
     * Get the cell content of a single row and columns lookup.
     * @param rowNumber     The row for getting the desired information.
     * @param headerName    The column name for looking up the desired information.
     * @return              The cell content given the row and column parameters.
     */
    public String getFieldFromRow(int rowNumber, String headerName) {
        // Establish the row that will be read from
        Row desiredRow = this.sheet.getRow(rowNumber);

        // Read target field based on the given Excel header name
        int columnNum = this.getColumnIndex(headerName);
        Cell cellContent = desiredRow.getCell(columnNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return this.printCellContent(cellContent);
    }

    /**
     * Get all the cell contents given the list of header names and the row number.
     * @param rowNumber     The row for getting all the desired information.
     * @param headerNames   An ordered list of the top-row header names.
     * @return              An ordered list of the desired cell content of the given row.
     */
    public String[] getFieldsFromRow(int rowNumber, String[] headerNames) {
        // Establish the row that will be read from
        Row desiredRow = this.sheet.getRow(rowNumber);

        // Go through each header name of the given list and get the cell content from the established desired row
        ArrayList<String> desiredInfo = new ArrayList<>();
        for (String headerName : headerNames) {
            int columnNum = this.getColumnIndex(headerName);
            Cell cellContent = desiredRow.getCell(columnNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            desiredInfo.add(this.printCellContent(cellContent));
        }
        return desiredInfo.toArray(new String[desiredInfo.size()]);
    }

    /**
     * Helper method to read the contents of an Excel cell.
     * @param cellToRead    The cell to read
     */
    private String printCellContent(Cell cellToRead) {
        String numericToString;

        switch (cellToRead.getCellTypeEnum()) {
            case STRING:
                return cellToRead.getStringCellValue();
            case NUMERIC:
                numericToString = Double.toString(cellToRead.getNumericCellValue());
                numericToString = this.removeDotZeroFromStringEnd(numericToString);
                return numericToString;
            case BOOLEAN:
                return Boolean.toString(cellToRead.getBooleanCellValue());
            case FORMULA:
                switch (cellToRead.getCachedFormulaResultTypeEnum()) {
                    case STRING:
                        numericToString = cellToRead.getStringCellValue();
                        numericToString = removeDotZeroFromStringEnd(numericToString);
                        return numericToString;
                    case NUMERIC:
                        numericToString = Double.toString(cellToRead.getNumericCellValue());
                        numericToString = this.removeDotZeroFromStringEnd(numericToString);
                        return numericToString;
                }
            default:
                return "";
        }
    }

    /**
     * Helper method for removing the trailing ".0" characters at the end of cell contents.
     * @param numericStringToCleanup    The numeric value converted to String
     * @return                          The resulting String
     */
    private String removeDotZeroFromStringEnd(String numericStringToCleanup) {
        int stringLength = numericStringToCleanup.length();
        if (stringLength > 2)
            if (numericStringToCleanup.substring(stringLength - 2, stringLength).equalsIgnoreCase(".0"))
                numericStringToCleanup = numericStringToCleanup.substring(0, numericStringToCleanup.length() - 2);
        return numericStringToCleanup;
    }
}