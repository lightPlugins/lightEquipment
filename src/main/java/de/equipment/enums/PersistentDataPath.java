package de.equipment.enums;

public enum PersistentDataPath {

    //  Consumable types

    CONSUMABLE_TYPE("CONSUMABLE_TYPE"),

    //  Tool types

    TOOL_TYPE("TOOL_TYPE"),

    //  Pickaxe stages

    PICKAXE_STAGE("PICKAXE_STAGE"),

    //  Item Types

    ITEM_TYPE("ITEM_TYPE"),

    ;

    private String type;
    PersistentDataPath(String type) { this.type = type; }
    public String getType() {

        return type;
    }
}
