package configuration;

public enum ConfigurationType {
    PRINT               ("print"),
    EXCEL_LOOKUP        ("excelLookup"),
    CHECKBOX_SELECTION  ("checkbox");

    /**
     * Enum text value.
     */
    private String type;

    /**
     * Constructor to associate enum with a text value.
     * @param type
     */
    ConfigurationType(String type) {
        this.type = type;
    }

    /**
     * Get enum text value.
     * @return
     */
    public String getValue() { return this.type; }

    /**
     * Parse string to its appropriate ConfigurationType enum type.
     * @param text      The enum text value.
     * @return          The appropriate enum type given its text value.
     */
    public static ConfigurationType fromString(String text) {
        for (ConfigurationType confEnum : ConfigurationType.values()) {
            if (confEnum.type.equalsIgnoreCase(text)) {
                return confEnum;
            }
        }
        return null;
    }
}
