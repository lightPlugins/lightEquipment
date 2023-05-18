package de.equipment.enums;

public enum PersistentDataPath {

    MONEY_VALUE("EXAMPLE_DATA"),
    ;

    private String type;
    PersistentDataPath(String type) { this.type = type; }
    public String getType() {

        return type;
    }
}
