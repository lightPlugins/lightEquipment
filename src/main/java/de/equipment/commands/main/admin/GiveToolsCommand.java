package de.equipment.commands.main.admin;

import de.equipment.enums.PermissionPath;
import de.equipment.items.PickaxeManager;
import de.equipment.master.Main;
import de.equipment.utils.SubCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class GiveToolsCommand extends SubCommand {


    @Override
    public String getName() {
        return "admin";
    }

    @Override
    public String getDescription() {
        return "admin commands";
    }

    @Override
    public String getSyntax() {
        return "/ashura admin pickaxe [stage]";
    }

    @Override
    public boolean perform(Player player, String[] args) {

        if(args.length == 3) {
            if(args[1].equalsIgnoreCase("pickaxe")) {

                if(!player.hasPermission(PermissionPath.AdminPickAxe.getPerm())) {
                    Main.util.sendMessage(player, "&cDu hast für diesen Befehl keine Berechtigung!");
                    return false;
                }

                try {
                    int stage = Integer.parseInt(args[2]);
                    PickaxeManager pickaxeManager = new PickaxeManager();
                    ItemStack itemStack = pickaxeManager.getPickaxe(stage, "pickaxe");

                    if(itemStack.getType().equals(Material.DIRT)) {
                        Main.util.sendMessage(player, "&cTool not found in the config!");
                        return false;
                    }

                    boolean isInventoryFull = player.getInventory().firstEmpty() == -1;

                    if(isInventoryFull) {
                        Main.util.sendMessage(player, "&cDein Inventar ist voll!");
                        return false;
                    }

                    player.getInventory().addItem(itemStack);
                    Main.util.sendMessage(player,
                            "&7Du hast "
                                    + Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName()
                                    + " &7+"
                                    + stage
                                    + " bekommen.");
                    return false;

                } catch (NumberFormatException ex) {
                    return false;
                }
            }
        }
        return false;
    }
}
