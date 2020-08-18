package us.thezircon.play.simplisticshops.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.menus.Shops;

public class Shop implements CommandExecutor {

    private static final SimplisticShops PLUGIN = SimplisticShops.getPlugin(SimplisticShops.class);
    private static Sound saleCompleteSound = Sound.valueOf(PLUGIN.getConfig().getString("SellSettings.Sounds.sellSound"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) { // Only open GUI for players
            Player player = (Player) sender;
            player.playSound(player.getLocation(), saleCompleteSound, 3, 1); // play sound
            new Shops(SimplisticShops.getPlayerMenuUtility(player)).open(); // open gui
        } else {
            sender.sendMessage("Must be a player to open the menu");
        }
        return false;
    }
}
