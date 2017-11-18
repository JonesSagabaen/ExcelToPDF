package configuration;

import java.util.Arrays;

public class ExcelPDFConfiguration {

    /**
     * The font size for writing onto the PDF document.
     */
    private int fontSize;

    /**
     * The x coordinate location for where the writing annotations will be done.
     */
    private int coordinateX;

    /**
     * The y coordinate location for where the writing annotations will be done.
     */
    private int coordinateY;

    /**
     * Separator used for reading/writing configuration files.
     */
    private final static String delimiter = "|||";

    /**
     * Regex used for parsing a string representation of a configuration file.
     */
    private final static String regexDelimiter = "\\|\\|\\|";

    /**
     * The delimiter value for separating configuration details in the configuration file.
     * @return                          The delimiter value used by all configurations in this application.
     */
    public static String getDelimiter() {
        return delimiter;
    }

    /**
     * Get the font size for this configuration.
     * @return                          The font size.
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Set the font size for this configuration.
     * @param fontSize                  The font size.
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    /**
     * Get the x coordinate for writing text onto the PDF document.
     * @return                          The x coordinate for where the writing annotations are to be done on the PDF document.
     */
    public int getX() {
        return coordinateX;
    }

    /**
     * Set the x coordinate for writing text onto the PDF document.
     * @param x                          The x coordinate for where the writing annotations are to be done on the PDF document.
     */
    public void setX(int x) {
        coordinateX = x;
    }

    /**
     * Get the y coordinate for writing text onto the PDF document.
     * @return                          The y coordinate for where the writing annotations are to be done on the PDF document.
     */
    public int getY() {
        return coordinateY;
    }

    /**
     * Set the y coordinate for writing text onto the PDF document.
     * @param y                          The y coordinate for where the writing annotations are to be done on the PDF document.
     */
    public void setY(int y) {
        coordinateY = y;
    }

    /**
     * Convert a string representation of a configuration.ExcelPDFConfiguration object.
     * @param input                     The string representation of a configuration.ExcelPDFConfiguration object.
     * @return                          Converted configuration.ExcelPDFConfiguration object.
     */
    public static ExcelPDFConfiguration parseString(String input) {
        String[] parsedArray = input.split(regexDelimiter);
        ConfigurationType configurationType = ConfigurationType.fromString(parsedArray[1]);
        switch (configurationType) {
            case PRINT:
                return new ConfigurationPrint(Arrays.copyOfRange(parsedArray, 2, parsedArray.length));
            case EXCEL_LOOKUP:
                return new ConfigurationLookup(Arrays.copyOfRange(parsedArray, 2, parsedArray.length));
            case CHECKBOX_SELECTION:
                return new ConfigurationCheckbox(Arrays.copyOfRange(parsedArray, 2, parsedArray.length));
        }
        return null;
    }
}
