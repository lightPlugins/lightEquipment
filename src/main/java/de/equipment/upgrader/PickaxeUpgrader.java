package de.equipment.upgrader;

import de.equipment.enums.PersistentDataPath;
import de.equipment.items.PickaxeManager;
import de.equipment.master.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class PickaxeUpgrader {


    private int getStage(ItemStack is) {

        PersistentDataContainer data =
                Objects.requireNonNull(is.getItemMeta()).getPersistentDataContainer();

        NamespacedKey key = new NamespacedKey(Main.getInstance, PersistentDataPath.PICKAXE_STAGE.getType());


        if (key.getKey().equalsIgnoreCase(PersistentDataPath.PICKAXE_STAGE.getType())) {
            if (!data.has(key, PersistentDataType.INTEGER)) {
                return -1;  // return -1 means no data from pickaxe
            }

            Integer currentType = data.get(key, PersistentDataType.INTEGER);

            if (currentType != null) {
                return currentType;
            }
        }
        return -1;
    }

    public ItemStack getNewStage(ItemStack is, Player player) {

        int currentStage = getStage(is);

        if(currentStage >= -1) {

            PickaxeManager pickaxeManager = new PickaxeManager();

            int newStage = currentStage + 1;
            return pickaxeManager.getPickaxe(newStage, "pickaxe");

        }
        return null;
    }
}
