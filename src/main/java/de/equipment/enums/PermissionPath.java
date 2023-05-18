package de.equipment.enums;

public enum PermissionPath {

    /*
        Admin Command Perissions
     */

    MoneyAdd("lighteconomy.admin.command.moneyadd"),

    /*
        User Command Perissions
     */

    PayCommand("lighteconomy.user.command.pay"),
    ;

    private final String path;
    PermissionPath(String path) { this.path = path; }
    public String getPerm() {
        return path;
    }
}
