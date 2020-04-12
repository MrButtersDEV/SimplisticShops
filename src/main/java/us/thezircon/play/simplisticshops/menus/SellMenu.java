package us.thezircon.play.simplisticshops.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.util.Objects;
import java.util.logging.Logger;

public class SellMenu {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    public static void openMenu(Player player) {

        String SellMenuTitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("SellSettings.Menu.title")));
        int size = (9*plugin.getConfig().getInt("SellSettings.Menu.rows"));

        Inventory gui = Bukkit.createInventory(player, size, SellMenuTitle);
        player.openInventory(gui);
    }
}
