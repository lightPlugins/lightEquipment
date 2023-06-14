package de.equipment.listener;

import de.equipment.enums.PersistentDataPath;
import de.equipment.items.ItemsManager;
import de.equipment.items.PickaxeManager;
import de.equipment.master.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.logging.Level;

public class DropsPickaxeListener implements Listener {

    @EventHandler
    public void onBlockBreakWithPickaxe(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material blockMaterial = block.getType();
        World world = player.getWorld();
        String worldName = world.getName();
        ItemStack currentTool = player.getItemInHand();

        FileConfiguration tools = Main.tools.getConfig();
        PickaxeManager pickaxe = new PickaxeManager();

        Bukkit.getLogger().log(Level.WARNING, "TEST-3 ");

        //  if(currentTool == null) { return; }

        Bukkit.getLogger().log(Level.WARNING, "TEST-2 ");

        // check if this tool not allowed in current World

        for(String blacklistWorld : tools.getStringList("tools.pickaxe.world-blacklist")) {
            Bukkit.getLogger().log(Level.WARNING, "TEST-1 ");
            if(blacklistWorld.equalsIgnoreCase(worldName)) {
                Bukkit.getLogger().log(Level.WARNING, "TEST0 ");
                return;
            }
        }


        PersistentDataContainer data =
                Objects.requireNonNull(currentTool.getItemMeta()).getPersistentDataContainer();

        NamespacedKey toolTypeKey =
                new NamespacedKey(Main.getInstance, PersistentDataPath.TOOL_TYPE.getType());
        NamespacedKey toolStageKey =
                new NamespacedKey(Main.getInstance, PersistentDataPath.PICKAXE_STAGE.getType());

        Bukkit.getLogger().log(Level.WARNING, "TEST1 ");

        if(toolTypeKey.getKey().equalsIgnoreCase(PersistentDataPath.TOOL_TYPE.getType())) {
            if(!data.has(toolTypeKey, PersistentDataType.STRING)) {
                return;
            }

            if(toolStageKey.getKey().equalsIgnoreCase(PersistentDataPath.PICKAXE_STAGE.getType())) {
                if(!data.has(toolStageKey, PersistentDataType.INTEGER)) {
                    return;
                }

                //  Pickaxe has tool type and current tool stage

                Bukkit.getLogger().log(Level.WARNING, "TEST4 ");

                String toolType = data.get(toolTypeKey, PersistentDataType.STRING);

                if(toolType == null) { return;  }
                Bukkit.getLogger().log(Level.WARNING, "TEST5 " + toolType);
                if(!toolType.equalsIgnoreCase("pickaxe")) { return;  }
                Bukkit.getLogger().log(Level.WARNING, "TEST6 ");

                Integer stageDummy = data.get(toolStageKey, PersistentDataType.INTEGER);

                if(stageDummy == null) { return; }
                Bukkit.getLogger().log(Level.WARNING, "TEST7 ");
                int toolStage = stageDummy;

                for(String singleDrop : tools.getStringList("tools.pickaxe.stage." + toolStage + ".drops")) {

                    String[] dropSetting = singleDrop.split(";");

                    Material dropFrom = Material.valueOf(dropSetting[0]);
                    String dropItem = dropSetting[1];
                    double dropChance = Double.parseDouble(dropSetting[2]);

                    Bukkit.getLogger().log(Level.WARNING, "TEST8 " + dropFrom + " " + dropItem + " " + dropChance + " " + blockMaterial);

                    //  Check if the block from config is broken
                    if(blockMaterial.equals(dropFrom)) {
                        double chance = Main.util.getRandomDoubleFromRange(0.0, 100.0);

                        Bukkit.getLogger().log(Level.WARNING, "TEST9 " + chance);

                        if(chance <= dropChance) {

                            Bukkit.getLogger().log(Level.WARNING, "TEST10 ");

                            //  TODO: Add Drop ItemStack from items.ynl with Item here

                            ItemsManager itemsManager = new ItemsManager();

                            ItemStack dropper = itemsManager.getCustomItem(dropItem);
                            ItemMeta dropperMeta = dropper.getItemMeta();

                            Item item = world.dropItemNaturally(block.getLocation(), dropper);

                            assert dropperMeta != null;

                            Bukkit.getLogger().log(Level.WARNING, "TEST12 ");

                            item.setCustomName(dropperMeta.getDisplayName());
                            item.setCustomNameVisible(true);

                        }
                    }
                }
            }
        }
    }
}
