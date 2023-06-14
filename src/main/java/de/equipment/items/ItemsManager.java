package de.equipment.items;

import de.equipment.enums.PersistentDataPath;
import de.equipment.master.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemsManager {


    public ItemStack getCustomItem(String itemName) {

        FileConfiguration items = Main.items.getConfig();


        Material material = Material.valueOf(items.getString("items." + itemName + ".material"));
        int modelData = items.getInt("items." + itemName + ".custom-model-data");
        String itemType = items.getString("items." + itemName + ".data");
        boolean glow = items.getBoolean("items." + itemName + ".glow");

        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();

        assert im != null;

        if(glow) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        String displayName = Main.colorTranslation.hexTranslation(
                items.getString("items." + itemName + ".display-name"));

        List<String> loreList = new ArrayList<>();

        items.getStringList("items." + itemName + ".lore").forEach(
                line -> loreList.add(Main.colorTranslation.hexTranslation(line)));

        if(itemType != null) {
            PersistentDataContainer data = im.getPersistentDataContainer();
            NamespacedKey toolTypeKey = new NamespacedKey(
                    Main.getInstance, PersistentDataPath.ITEM_TYPE.getType());

            //  NBT Tags for item type
            if(toolTypeKey.getKey().equalsIgnoreCase(PersistentDataPath.TOOL_TYPE.getType())) {
                data.set(toolTypeKey, PersistentDataType.STRING, itemType);
            }
        }

        im.setLore(loreList);
        im.setCustomModelData(modelData);
        im.setDisplayName(displayName);

        is.setItemMeta(im);

        return is;

    }

}
