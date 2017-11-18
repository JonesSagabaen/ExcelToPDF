package configuration;

public class ConfigurationLookup extends ExcelPDFConfiguration {

    /**
     * The column for performing a lookup in the Excel document.  The result of which will then be written to
     * the PDF document.
     */
    private String excelTargetColumn;

    /**
     * Get the target column for performing a lookup in the Excel document.
     * @return              The target column to perform a lookup in.
     */
    public String getExcelTargetColumn() {
        return excelTargetColumn;
    }

    /**
     * Constructor for reading its appropriate configuration string representation.
     * Example of valid configuration string representation:
     *      |||excelLookup|||20|||name|||210|||540|||
     * @param strings       A valid string representation of this configuration type.
     */
    public ConfigurationLookup(String[] strings) {
        setFontSize(Integer.parseInt(strings[0]));
        excelTargetColumn = strings[1];
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
                ConfigurationType.EXCEL_LOOKUP.getValue() + getDelimiter() +
                getFontSize() + getDelimiter() +
                getExcelTargetColumn() + getDelimiter() +
                getX() + getDelimiter() +
                getY() + getDelimiter();
    }
}
