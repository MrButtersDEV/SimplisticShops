package us.thezircon.play.simplisticshops.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Prices implements TabExecutor {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    final File Shops = new File(plugin.getDataFolder(), "Shops");
    final File Sell = new File(Shops,"Sell");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
           Player player = (Player) sender;

           if (args.length==1) {
               String ShopName = args[0];
               try {
                   for (File Shops : Sell.listFiles()) {
                       YamlConfiguration Shop = YamlConfiguration.loadConfiguration(Shops);
                       if (Shop.getString("Shop.Name").equals(ShopName)) {
                         //  PricesMenu.open(player, Shops);
                           break;
                       }
                   }
               } catch (NullPointerException err) {
                   player.sendMessage("No shops available");
               }
           } else {
               player.sendMessage("Unknown Args: /prices [shopname]");
           }

        } else {sender.sendMessage("You need to be a player to do this");}
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        List<String> Shops = new ArrayList<>();
        for (File f : Sell.listFiles()) {
            YamlConfiguration Shop = YamlConfiguration.loadConfiguration(f);
            Shops.add(Shop.getString("Shop.Name"));
        }

        if (args.length==1) {
            return Shops;
        }

        return null;
    }
}
