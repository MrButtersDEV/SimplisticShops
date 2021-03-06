package us.thezircon.play.simplisticshops.commands.SetPrice;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.commands.SetPrice.subcommands.buy;
import us.thezircon.play.simplisticshops.commands.SetPrice.subcommands.sell;
import us.thezircon.play.simplisticshops.commands.cmdManager;

import java.util.ArrayList;
import java.util.List;

public class SetPrice implements TabExecutor {
    private ArrayList<cmdManager> subcommands = new ArrayList<>();

    public SetPrice() {
        subcommands.add(new buy());
        subcommands.add(new sell());
    }

    public ArrayList<cmdManager> getSubCommands() {
        return subcommands;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0 && sender.hasPermission("SimplisticShops.SetPrices")) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    getSubCommands().get(i).perform(sender, args);
                }
            }
        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            return null;
        }

        if (args.length == 1) {
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;
        } else if (args.length == 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).arg1((Player) sender, args);
                }
            }
        } else if (args.length == 3) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).arg2((Player) sender, args);
                }
            }
        } else if (args.length == 4) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).arg3((Player) sender, args);
                }
            }
        }
        return null;
    }
}