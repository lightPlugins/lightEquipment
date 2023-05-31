package de.equipment.maps;

import de.equipment.master.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.imageio.stream.IIOByteBuffer;

public class MapManager implements Listener {


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {


        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = event.getPlayer().getWorld();
        Location blockLocation = block.getLocation();

        if(world.getName().equalsIgnoreCase("test")) {
            if(block.getType().equals(Material.COAL_ORE)) {

                /*
                    Drop Item from Item list, if the specified Block breaks
                 */

                player.getWorld().getBlockAt(blockLocation).setType(Material.BEDROCK);
                event.setCancelled(true);

                ItemStack  itemStack = new ItemStack(Material.COAL_ORE, 10);
                ItemMeta itemMeta = itemStack.getItemMeta();

                assert itemMeta != null;
                itemMeta.setDisplayName("§cDas ist ein Test");

                itemStack.setItemMeta(itemMeta);



                Item item = world.dropItemNaturally(blockLocation, itemStack);
                item.setCustomName("§cDas ist ein Test");
                item.setCustomNameVisible(true);

                BukkitTask task = new BukkitRunnable() {

                    final int[] counter = {0};
                    @Override
                    public void run() {
                        if(counter[0] >= 5) {

                            player.getWorld().getBlockAt(blockLocation).setType(Material.COAL_ORE);
                            cancel();
                        }
                        counter[0] ++;
                    }
                }.runTaskTimer(Main.getInstance, 0, 20);
            }
        }


    }
}
