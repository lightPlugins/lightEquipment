package de.equipment.items;

import de.equipment.enums.PersistentDataPath;
import de.equipment.master.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PickaxeManager {


    public ItemStack getPickaxe(int stage, String itemName) {

        FileConfiguration tools = Main.tools.getConfig();

        Material material = Material.valueOf(tools.getString("tools." + itemName + ".material"));
        int modelData = tools.getInt("tools." + itemName + "." + stage + ".custom-model-data");
        Bukkit.getLogger().log(Level.WARNING, "TEST: " + tools.getString("tools." + itemName + ".stage"));

        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();

        assert im != null;

        String displayName = Main.colorTranslation.hexTranslation(
                tools.getString("tools." + itemName + ".stage." + stage + ".display-name"));

        List<String> loreList = new ArrayList<>();

        tools.getStringList("tools." + itemName + ".stage." + stage + ".lore").forEach(
                line -> loreList.add(Main.colorTranslation.hexTranslation(line)));

        if(itemName != null) {
            PersistentDataContainer data = im.getPersistentDataContainer();
            NamespacedKey toolTypeKey = new NamespacedKey(
                    Main.getInstance, PersistentDataPath.TOOL_TYPE.getType());
            NamespacedKey pickaxeStage = new NamespacedKey(
                    Main.getInstance, PersistentDataPath.PICKAXE_STAGE.getType());

            //  NBT Tags for tool kind
            if(toolTypeKey.getKey().equalsIgnoreCase(PersistentDataPath.TOOL_TYPE.getType())) {
                data.set(toolTypeKey, PersistentDataType.STRING, itemName);
            }
            //  NBT Tags for tool stage 1-30
            if(pickaxeStage.getKey().equalsIgnoreCase(PersistentDataPath.PICKAXE_STAGE.getType())) {
                data.set(pickaxeStage, PersistentDataType.INTEGER, stage);
            }
        }

        im.setLore(loreList);
        im.setCustomModelData(modelData);
        im.setDisplayName(displayName);

        is.setItemMeta(im);

        return is;

    }
}
