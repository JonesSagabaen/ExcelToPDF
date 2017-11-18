package configuration;

import java.util.Arrays;

public class ConfigurationCheckbox extends ExcelPDFConfiguration {

    private String excelTargetColumn;

    private String checkboxSelection;

    private String[] rawCheckboxMappingSet;

    public String getExcelTargetColumn() {
        return excelTargetColumn;
    }

    public String getCheckboxSelection() {
        return checkboxSelection;
    }

    public boolean setCheckboxSelection(String checkboxSelection) {
        if (checkboxSelection.isEmpty()) {
            return false;
        }
        else {
            for (int i = 0; i < rawCheckboxMappingSet.length; i = i + 3) {
                if (rawCheckboxMappingSet[i].equalsIgnoreCase(checkboxSelection)) {
                    this.checkboxSelection = checkboxSelection;
                    setX(Integer.parseInt(rawCheckboxMappingSet[i + 1]));
                    setY(Integer.parseInt(rawCheckboxMappingSet[i + 2]));
                }
            }
            return true;
        }
    }

    public ConfigurationCheckbox(String[] strings) {
        setFontSize(Integer.parseInt(strings[0]));
        excelTargetColumn = strings[1];
        rawCheckboxMappingSet = Arrays.copyOfRange(strings, 2, strings.length);
    }

    @Override
    public String toString() {
        String result = getDelimiter() +
                ConfigurationType.CHECKBOX_SELECTION.getValue() + getDelimiter() +
                getFontSize() + getDelimiter() +
                getExcelTargetColumn() + getDelimiter();
        for (String s : rawCheckboxMappingSet) {
            result = result + s + getDelimiter();
        }
        return result;
    }
}
