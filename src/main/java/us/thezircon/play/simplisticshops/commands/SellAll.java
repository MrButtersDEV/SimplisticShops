package us.thezircon.play.simplisticshops.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.utils.Seller;

import java.util.logging.Logger;

public class SellAll implements CommandExecutor{

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Sound menuOpenSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuOpenSound"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory inv = player.getInventory();

            Seller.sellItems(player, inv, false);
            player.playSound(player.getLocation(), menuOpenSound, 3, 1);

        } else {
            log.info(String.format("[%s] - This command is for players only", plugin.getDescription().getName()));
        }
        return false;
    }
}
