package de.equipment.listener;

import de.equipment.enums.PersistentDataPath;
import de.equipment.items.PickaxeManager;
import de.equipment.master.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class DropsPickaxe implements Listener {



    @EventHandler
    public void onBlockBreakWithPickaxe(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material blockMaterial = block.getType();
        World world = player.getWorld();
        String worldName = world.getName();
        ItemStack currentTool = player.getItemInUse();

        FileConfiguration tools = Main.tools.getConfig();
        PickaxeManager pickaxe = new PickaxeManager();

        if(currentTool == null) { return; }

        // check if this tool not allowed in current World

        for(String blacklistWorld : tools.getStringList("tools.pickaxe.world-blacklist")) {
            if(blacklistWorld.equalsIgnoreCase(worldName)) {
                return;
            }
        }


        PersistentDataContainer data =
                Objects.requireNonNull(currentTool.getItemMeta()).getPersistentDataContainer();

        NamespacedKey toolTypeKey =
                new NamespacedKey(Main.getInstance, PersistentDataPath.TOOL_TYPE.getType());
        NamespacedKey toolStageKey =
                new NamespacedKey(Main.getInstance, PersistentDataPath.PICKAXE_STAGE.getType());

        if(toolTypeKey.getKey().equalsIgnoreCase(PersistentDataPath.TOOL_TYPE.getType())) {
            if(!data.has(toolTypeKey, PersistentDataType.STRING)) {
                return;
            }

            if(toolStageKey.getKey().equalsIgnoreCase(PersistentDataPath.PICKAXE_STAGE.getType())) {
                if(!data.has(toolStageKey, PersistentDataType.INTEGER)) {
                    return;
                }

                //  Pickaxe has tool type and current tool stage



                String toolType = data.get(toolTypeKey, PersistentDataType.STRING);

                if(toolType == null) { return;  }
                if(!toolType.equalsIgnoreCase("pickaxe")) { return;  }

                Integer stageDummy = data.get(toolStageKey, PersistentDataType.INTEGER);

                if(stageDummy == null) { return; }
                int toolStage = stageDummy;

                for(String singleDrop : tools.getStringList("tools.pickaxe.stage." + toolStage + ".drops")) {

                    String[] dropSetting = singleDrop.split(";");

                    Material dropFrom = Material.valueOf(dropSetting[0]);
                    String dropItem = dropSetting[1];
                    double dropChance = Double.parseDouble(dropSetting[2]);

                    //  Check if the block from config is broken
                    if(blockMaterial.equals(dropFrom)) {
                        double chance = Main.util.getRandomDoubleFromRange(0.0, 100.0);

                        if(chance <= dropChance) {

                            //  TODO: Add Drop ItemStack from items.ynl with Item here

                        }
                    }
                }
            }
        }
    }
}
