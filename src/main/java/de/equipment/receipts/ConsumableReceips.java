package de.equipment.receipts;

import de.equipment.consumables.ConsumableManager;
import de.equipment.master.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConsumableReceips {

    public void onCustomCrafting() {
        FileConfiguration consumable = Main.consumable.getConfig();


        for(String path : consumable.getConfigurationSection("consumable").getKeys(false)) {


            ConsumableManager consumableManager = new ConsumableManager();

            ItemStack newItem = consumableManager.getConsumableItem(path);

            Bukkit.getLogger().log(Level.SEVERE, "TEST " + path.toLowerCase());

            ShapedRecipe shapedRecipe =
                    new ShapedRecipe(NamespacedKey.minecraft(path.toLowerCase()), newItem);

            List<String> loreList =
                    new ArrayList<>(consumable.getStringList("consumable." + path + ".crafting.receipt"));

            shapedRecipe.shape(loreList.get(0), loreList.get(1), loreList.get(2));

            for(String declare : consumable.getStringList("consumable." + path + ".crafting.declare")) {


                String[] test = declare.split(";");

                Material mat = Material.valueOf(test[1]);

                if(test[1].equalsIgnoreCase("custom")) {
                    //  for custom items from this plugin
                }

                shapedRecipe.setIngredient(test[0].charAt(0),
                        new RecipeChoice.ExactChoice(new ItemStack(mat)));
                Bukkit.getServer().addRecipe(shapedRecipe);
                Bukkit.getLogger().log(Level.WARNING, "new Receipt: " + path);
            }
        }
    }
}
