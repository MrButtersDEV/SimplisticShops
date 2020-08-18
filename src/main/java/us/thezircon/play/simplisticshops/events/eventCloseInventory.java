package us.thezircon.play.simplisticshops.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.utils.Seller;

import java.util.Objects;
import java.util.logging.Logger;

public class eventCloseInventory implements Listener {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    String SellMenuTitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("SellSettings.Menu.title")));
    private static Sound menuCloseSound = Sound.valueOf(plugin.getConfig().getString("SellSettings.Sounds.menuCloseSound"));

    @EventHandler
    public void closeInv(InventoryCloseEvent e){
        Player player = Bukkit.getPlayer(e.getPlayer().getUniqueId());

        Inventory inv = e.getInventory();

        /*
        //Lock items in Shops Menu
        if (e.getView().getTitle().equals(SellMenuTitle)){
            player.playSound(player.getLocation(), menuCloseSound, 1, 1);

            Seller.sellItems(player, inv, true);
        } else if (e.getView().getTitle().equals(BuyMenu.getTitle())) {
            plugin.hmChkOut.remove(player);
        }
        */
    }
}