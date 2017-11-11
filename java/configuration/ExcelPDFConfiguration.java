package configuration;

public class ExcelPDFConfiguration {

    /**
     * The target column name when reading from the provided Excel document.
     */
    private String targetExcelColumnName;

    /**
     * The x-coordinate needed for placing text on the PDF document.
     */
    private int x;

    /**
     * The y-coordinate needed for placing text on the PDF document.
     */
    private int y;

    /**
     * The font size for writing onto the PDF document.
     */
    private int fontSize;

    /**
     * Separator used for reading/writing configuration files.
     */
    private final static String delimiter = "|||";

    /**
     * Regex used for parsing a string representation of a configuration file.
     */
    private final static String regexDelimiter = "\\|\\|\\|";

    /**
     * The configuration file will save a set of information grouping Excel text with placement details for inserting
     * into a PDF document.
     * @param targetExcelColumnName     The name of the target column that is needed for when reading the Excel document.
     * @param x                         The x-coordinate to place the text onto the PDF.  (Origin is bottom-left of PDF document.)
     * @param y                         The y-coordinate to place the text onto the PDF.  (Origin is bottom-left of PDF document.)
     * @param fontSize                  The font size of the text that is placed onto the PDF document.
     */
    public ExcelPDFConfiguration(String targetExcelColumnName, int x, int y, int fontSize) {
        this.targetExcelColumnName = targetExcelColumnName;
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
    }

    /**
     * Get the Excel column name that is being read from.
     * @return                          Name of the Excel column name being read from.
     */
    public String getTargetExcelColumnName() {
        return targetExcelColumnName;
    }

    /**
     * Get the x-coordinate for writing the desired text to the PDF document.
     * @return                          The x-coordinate to write onto the PDF document.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the x\y-coordinate for writing the desired text to the PDF document.
     * @return                          The y-coordinate to write onto the PDF document.
     */
    public int getY() {
        return y;
    }

    /**
     * The font size of the text that will be written to the PDF document's text field.
     * @return                          The font size of the text to be written into the PDF document's text field.
     */
    public int getFontSize() {
        return fontSize;
    }

    @Override
    public String toString() {
        return delimiter +
                targetExcelColumnName + delimiter +
                x + delimiter +
                y + delimiter +
                fontSize + delimiter;
    }


    /**
     * Convert a string representation of a configuration.ExcelPDFConfiguration object.
     * @param input                     The string representation of a configuration.ExcelPDFConfiguration object.
     * @return                          Converted configuration.ExcelPDFConfiguration object.
     */
    public static ExcelPDFConfiguration parseString(String input) {
        String[] parsedArray = input.split(regexDelimiter);
        String columnName = parsedArray[1];
        int x = Integer.parseInt(parsedArray[2]);
        int y = Integer.parseInt(parsedArray[3]);
        int fontSize = Integer.parseInt(parsedArray[4]);

        return new ExcelPDFConfiguration(columnName, x, y, fontSize);
    }
}
