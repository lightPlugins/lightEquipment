package de.equipment.maps;

import de.equipment.consumables.ConsumableManager;
import de.equipment.items.PickaxeManager;
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
import org.bukkit.inventory.ItemStack;


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

                // player.getWorld().getBlockAt(blockLocation).setType(Material.BEDROCK);
                event.setCancelled(true);

                ConsumableManager consumableManager = new ConsumableManager();
                PickaxeManager pickaxeManager = new PickaxeManager();

                ItemStack itemStack = consumableManager.getConsumableItem("fruitOfLife");
                ItemStack pickaxe = pickaxeManager.getPickaxe(0, "pickaxe");

                //Item item = world.dropItemNaturally(blockLocation, itemStack);
                //Item item2 = world.dropItemNaturally(blockLocation, pickaxe);

                //item.setCustomName(itemStack.getItemMeta().getDisplayName());
                //item.setCustomNameVisible(true);

                //item2.setCustomName(pickaxe.getItemMeta().getDisplayName());
                //item2.setCustomNameVisible(true);

                /*
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
                }.runTaskTimer(Main.getInstance, 0, 20); */
            }
        }


    }
}
