package configuration;

import java.util.Arrays;

public class ConfigurationCheckbox extends ExcelPDFConfiguration {

    /**
     * The column for performing a lookup in the Excel document.  The result of which will then be written to
     * the PDF document.
     */
    private String excelTargetColumn;

    /**
     * The result from reading the Excel lookup.
     */
    private String checkboxSelection;

    /**
     * The set of values the PDF checkboxes to mark and their locations.
     * The string array will be ordered with a checkbox value, its x coordinate, its y coordinate.  This array
     * will continue this ordering depending on how many checkboxes the PDF document may have.
     */
    private String[] rawCheckboxMappingSet;

    /**
     * Get the Excel target column for performing a lookup in.
     * @return                      The Excel target column.
     */
    public String getExcelTargetColumn() {
        return excelTargetColumn;
    }

    /**
     * Get the checkbox selection which comes from performing the Excel lookup.
     * @return                      The result of the Exxel lookup.
     */
    public String getCheckboxSelection() {
        return checkboxSelection;
    }

    /**
     * Use this after performing the desired Excel lookup.  The result of the lookup then needs to be sent with this
     * method.  Doing that will now set the x coordinate value and the y coordinate value which is needed for
     * checking the appropriate checkbox in the PDF document.
     * @param checkboxSelection     The checkbox selection found from performing the appropriate Excel lookup.
     * @return                      True if successfully set x, y coordinates.
     */
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

    /**
     * Constructor for reading its appropriate configuration string representation.
     * Example of valid configuration string representation:
     *      |||checkbox|||20|||newsletter signup|||yes|||431|||455|||no|||495|||455|||
     * @param strings       A valid string representation of this configuration type.
     */
    public ConfigurationCheckbox(String[] strings) {
        setFontSize(Integer.parseInt(strings[0]));
        excelTargetColumn = strings[1];
        rawCheckboxMappingSet = Arrays.copyOfRange(strings, 2, strings.length);
    }

    /**
     * Used for generating a configuration string representation.
     * @return              String representation of this type of configuration.
     */
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
