package de.equipment.listener;

import de.equipment.consumables.ConsumableManager;
import de.equipment.enums.PersistentDataPath;
import de.equipment.master.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.logging.Level;

public class ConsumableListener implements Listener {

    @EventHandler
    public void onConsumable(PlayerItemConsumeEvent event) {


        Player player = event.getPlayer();

        FileConfiguration consumable = Main.consumable.getConfig();

        for(String path : Objects.requireNonNull(
                consumable.getConfigurationSection("consumable")).getKeys(false)) {



            ConsumableManager consumableManager = new ConsumableManager();
            ItemStack is = consumableManager.getConsumableItem(path);
            ItemMeta im = is.getItemMeta();

            PersistentDataContainer data =
                    Objects.requireNonNull(event.getItem().getItemMeta()).getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Main.getInstance, PersistentDataPath.CONSUMABLE_TYPE.getType());

            String currentType = data.get(key, PersistentDataType.STRING);

            Bukkit.getLogger().log(Level.WARNING, "TEST " + currentType + " " + path);
            if(currentType == null) { return; }

            if(currentType.equalsIgnoreCase(path)) {
                if(key.getKey().equalsIgnoreCase(PersistentDataPath.CONSUMABLE_TYPE.getType())) {
                    if(!data.has(key, PersistentDataType.STRING)) {
                        return;
                    }

                    String consumableType = data.get(key, PersistentDataType.STRING);

                    assert consumableType != null;

                    int currentHungerLevel = player.getFoodLevel();
                    int configHungerLevel = consumable.getInt("consumable." + path + ".hunger");

                    if((currentHungerLevel + configHungerLevel) > 15) {
                        configHungerLevel = 15;
                    }

                    configHungerLevel += currentHungerLevel;

                    player.setFoodLevel(configHungerLevel);
                    Main.util.sendMessage(player,
                            "You got " + consumable.getInt("consumable." + path + ".hunger") + " hunger Points");

                }
            }
        }
    }
}
