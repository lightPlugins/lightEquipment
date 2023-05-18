package de.equipment.master;

import de.equipment.utils.ColorTranslation;
import de.equipment.utils.FileManager;
import de.equipment.utils.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main getInstance;
    private Economy econ;

    public static FileManager messages;
    public static FileManager settings;
    public static FileManager drops;
    public static FileManager items;

    public static ColorTranslation colorTranslation;
    public static Util util;


    public void onLoad() {

        /*  Initialize the Plugins instance  */

        getInstance = this;
    }

    public void onEnable() {

        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        messages = new FileManager(this,"messages.yml");
        settings = new FileManager(this,"settings.yml");
        drops = new FileManager(this,"drops.yml");
        items = new FileManager(this,"items.yml");

        colorTranslation = new ColorTranslation();
        util = new Util();

    }

    public void onDisable() {

    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public Economy getEconomy() {
        return econ;
    }
}