package us.thezircon.play.simplisticshops.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.menus.SellMenu;

import java.util.logging.Logger;

public class Sell implements CommandExecutor {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    private static Sound saleCompleteSound = Sound.valueOf(plugin.getConfig().getString("SellSettings.Sounds.sellSound"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SellMenu.openMenu(player);

            player.playSound(player.getLocation(), saleCompleteSound, 3, 1);

        } else {
            log.info(String.format("[%s] - This command is for players only", plugin.getDescription().getName()));
        }
        return false;
    }
}
