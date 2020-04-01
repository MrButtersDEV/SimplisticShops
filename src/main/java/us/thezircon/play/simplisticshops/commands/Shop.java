package us.thezircon.play.simplisticshops.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.menus.Shops;

public class Shop implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            Shops.openMenu(player);

        } else {
            sender.sendMessage("Must be a player to open the menu");
        }

        return false;
    }
}
