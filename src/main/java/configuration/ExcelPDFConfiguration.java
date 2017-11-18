package configuration;

import java.util.Arrays;

public class ExcelPDFConfiguration {

    /**
     * The font size for writing onto the PDF document.
     */
    private int fontSize;

    private int coordinateX;

    private int coordinateY;

    /**
     * Separator used for reading/writing configuration files.
     */
    private final static String delimiter = "|||";

    /**
     * Regex used for parsing a string representation of a configuration file.
     */
    private final static String regexDelimiter = "\\|\\|\\|";

    public static String getDelimiter() {
        return delimiter;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getX() {
        return coordinateX;
    }

    public void setX(int x) {
        coordinateX = x;
    }

    public int getY() {
        return coordinateY;
    }

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
