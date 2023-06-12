package de.equipment.items;

import de.equipment.master.Main;
import de.equipment.utils.FileManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class PickaxeManager {


    public ItemStack pickaxe(int stage, String itemName) {

        FileConfiguration tools = Main.tools.getConfig();


            Material material = Material.valueOf(tools.getString("tools."));

        ItemStack axe = new ItemStack(Material.COAL_ORE, 1);




        return axe;

    }
}
