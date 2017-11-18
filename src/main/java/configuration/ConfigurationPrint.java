package configuration;

public class ConfigurationPrint extends ExcelPDFConfiguration {

    /**
     * The plain text that is to be written to the PDF document.
     */
    private String printString;

    /**
     * Get the plain text to be written onto the PDF document.
     * @return              The plain text to write.
     */
    public String getPrintString() {
        return printString;
    }

    /**
     * Constructor for reading its appropriate configuration string representation.
     * Example of valid configuration string representation:
     *      |||print|||20|||111217|||450|||595|||
     * @param strings       A valid string representation of this configuration type.
     */
    public ConfigurationPrint(String[] strings) {
        setFontSize(Integer.parseInt(strings[0]));
        printString = strings[1];
        setX(Integer.parseInt(strings[2]));
        setY(Integer.parseInt(strings[3]));
    }

    /**
     * Used for generating a configuration string representation.
     * @return              String representation of this type of configuration.
     */
    @Override
    public String toString() {
        return getDelimiter() +
                ConfigurationType.PRINT.getValue() + getDelimiter() +
                getFontSize() + getDelimiter() +
                getPrintString() + getDelimiter() +
                getX() + getDelimiter() +
                getY() + getDelimiter();
    }
}
