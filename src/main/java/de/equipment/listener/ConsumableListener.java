package de.equipment.listener;

import de.equipment.consumables.ConsumableManager;
import de.equipment.enums.PersistentDataPath;
import de.equipment.master.Main;
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

public class ConsumableListener implements Listener {

    @EventHandler
    public void onConsumable(PlayerItemConsumeEvent event) {


        Player player = event.getPlayer();

        FileConfiguration consumable = Main.consumable.getConfig();

        for(String path : Objects.requireNonNull(
                consumable.getConfigurationSection("consumable")).getKeys(false)) {

            if(!Objects.requireNonNull(
                    consumable.getString("consumable." + path + ".type")).equalsIgnoreCase("food")) {
                return;
            }

            ConsumableManager consumableManager = new ConsumableManager();
            ItemStack is = consumableManager.getConsumableItem(path);
            ItemMeta im = is.getItemMeta();

            PersistentDataContainer data =
                    Objects.requireNonNull(event.getItem().getItemMeta()).getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Main.getInstance, PersistentDataPath.CONSUMABLE_TYPE.getType());

            if(key.getKey().equalsIgnoreCase(PersistentDataPath.CONSUMABLE_TYPE.getType())) {
                if(!data.has(key, PersistentDataType.STRING)) {
                    return;
                }

                String consumableType = data.get(key, PersistentDataType.STRING);

                assert consumableType != null;
                if(consumableType.equalsIgnoreCase("food")) {

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
