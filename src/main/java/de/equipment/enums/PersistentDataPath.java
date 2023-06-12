package de.equipment.enums;

public enum PersistentDataPath {

    CONSUMABLE_TYPE("CONSUMABLE_TYPE"),

    TOOL_TYPE("TOOL_TYPE"),

    PICKAXE_STAGE("PICKAXE_STAGE"),
    ;

    private String type;
    PersistentDataPath(String type) { this.type = type; }
    public String getType() {

        return type;
    }
}
