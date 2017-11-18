package configuration;

public class ConfigurationPrint extends ExcelPDFConfiguration {

    private String printString;

    public String getPrintString() {
        return printString;
    }

    public ConfigurationPrint(String[] strings) {
        setFontSize(Integer.parseInt(strings[0]));
        printString = strings[1];
        setX(Integer.parseInt(strings[2]));
        setY(Integer.parseInt(strings[3]));
    }

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
