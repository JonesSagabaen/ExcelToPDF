package configuration;

public class ConfigurationLookup extends ExcelPDFConfiguration {

    private String excelTargetColumn;

    public String getExcelTargetColumn() {
        return excelTargetColumn;
    }

    public ConfigurationLookup(String[] strings) {
        setFontSize(Integer.parseInt(strings[0]));
        excelTargetColumn = strings[1];
        setX(Integer.parseInt(strings[2]));
        setY(Integer.parseInt(strings[3]));
    }

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
