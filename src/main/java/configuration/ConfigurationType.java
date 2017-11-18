package configuration;

public enum ConfigurationType {
    PRINT               ("print"),
    EXCEL_LOOKUP        ("excelLookup"),
    CHECKBOX_SELECTION  ("checkbox");

    private String type;

    ConfigurationType(String type) {
        this.type = type;
    }

    public String getValue() { return this.type; }

    public static ConfigurationType fromString(String text) {
        for (ConfigurationType confEnum : ConfigurationType.values()) {
            if (confEnum.type.equalsIgnoreCase(text)) {
                return confEnum;
            }
        }
        return null;
    }
}
