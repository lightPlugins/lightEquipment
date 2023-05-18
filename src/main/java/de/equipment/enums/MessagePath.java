package de.equipment.enums;

import de.equipment.master.Main;
import org.bukkit.configuration.file.FileConfiguration;

public enum MessagePath {

    Prefix("prefix"),
    NoPermission("noPermission"),
    Reload("reload"),
    ;

    private final String path;

    MessagePath(String path) { this.path = path; }
    public String getPath() {
        FileConfiguration paths = Main.messages.getConfig();
        try {
            return paths.getString(this.path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
