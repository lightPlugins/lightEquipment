package de.equipment.commands.main;

import de.equipment.commands.main.admin.GiveToolsCommand;
import de.equipment.master.Main;
import de.equipment.utils.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainCommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }


    public Main plugin;
    public MainCommandManager(Main plugin) {
        this.plugin = plugin;
        subCommands.add(new GiveToolsCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                for(int i = 0; i < subCommands.size(); i++) {
                    if(args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {

                        try {
                            if(getSubCommands().get(i).perform(player, args)) {
                                // Main.debugPrinting.sendInfo("MainSubCommand " + Arrays.toString(args) + " successfully executed by " + player.getName());
                            }

                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {

                /* if the Main command is /money, just do here a quick balance checkout */
            }
        }

        return false;
    }

}
