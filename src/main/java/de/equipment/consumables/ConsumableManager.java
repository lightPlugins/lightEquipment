package de.equipment.consumables;


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

public class ConsumableManager {

    public ItemStack getConsumableItem(String itemName) {   // itemName = from consumable.yml

        FileConfiguration consumable = Main.consumable.getConfig();

        ItemStack is = new ItemStack(Material.DIRT);
        ItemMeta im = is.getItemMeta();

        Material material = Material.valueOf(consumable.getString("consumable." + itemName + ".material"));
        String displayName = Main.colorTranslation.hexTranslation(
                consumable.getString("consumable." + itemName + ".display-name"));
        boolean glow = consumable.getBoolean("consumable." + itemName + ".glow");
        int customModeData = consumable.getInt("consumable." + itemName + ".custom-model-data");
        String consumableType = consumable.getString("consumable." + itemName + ".type");

        assert im != null;
        is.setType(material);
        im.setDisplayName(displayName);

        if(customModeData != 0) {
            im.setCustomModelData(customModeData);
        }

        if(glow) {
            im.addEnchant(Enchantment.DURABILITY, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        List<String> loreList = new ArrayList<>();

        consumable.getStringList("consumable." + itemName + ".lore")
                .forEach(line -> loreList.add(Main.colorTranslation.hexTranslation(line)));

        im.setLore(loreList);

        if(consumableType != null) {
            PersistentDataContainer data = im.getPersistentDataContainer();
            NamespacedKey namespaceKeyValue = new NamespacedKey(
                    Main.getInstance, PersistentDataPath.CONSUMABLE_TYPE.getType());

            if(namespaceKeyValue.getKey().equalsIgnoreCase(PersistentDataPath.CONSUMABLE_TYPE.getType())) {
                data.set(namespaceKeyValue, PersistentDataType.STRING, consumableType);
            }
        }

        is.setItemMeta(im);

        return is;
    }
}
