package us.thezircon.play.simplisticshops.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.menus.BuyMenu;
import us.thezircon.play.simplisticshops.menus.ShopMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class eventClickInventory implements Listener {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    private static final File Shops = new File(plugin.getDataFolder(), "Shops");
    private static final File Buy = new File(Shops,"Buy");
    private static final File Sell = new File(Shops,"Sell");

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        String SellShopsMenuTitle = ChatColor.translateAlternateColorCodes('&', "&3Shops");
        String SellMenuTitle = ChatColor.translateAlternateColorCodes('&', "&9Checkout.....");

        Player player = (Player) e.getWhoClicked();

        //Checks for Shops Menu
        if (e.getView().getTitle().equals(SellShopsMenuTitle)) {
            e.setCancelled(true); // Locks Items for Shops menu

            //Gets Clicked Item
            File[] buyFiles = Buy.listFiles();
            if (buyFiles != null) {
                for (File file : buyFiles) {

                    FileConfiguration store = YamlConfiguration.loadConfiguration(file);

                    String name = ChatColor.translateAlternateColorCodes('&', store.getString("Shop.DisplayName"));
                    String item = store.getString("Shop.ShopIcon");
                    List<String> lore = new ArrayList<>();
                    for (String string : store.getStringList("Shop.Lore")) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, string)));
                    }

                    ItemStack icon = new ItemStack(Material.valueOf(item));
                    ItemMeta meta = icon.getItemMeta();
                    meta.setDisplayName(name);
                    meta.setLore(lore);
                    icon.setItemMeta(meta);

                    try {
                        if (e.getCurrentItem().equals(icon)) {
                            ShopMenu.openShop(player, file);
                            break;
                        }
                    } catch (NullPointerException ignored) {
                    }
                }
            }
        }

        //Check shops & lock menu
        File[] buyFiles = Buy.listFiles();
        if (buyFiles != null) {
            for (File file : buyFiles) {
                FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
                String shopName = ChatColor.translateAlternateColorCodes('&', shop.getString("Shop.DisplayName"));
                if (e.getView().getTitle().equals(shopName)){
                    e.setCancelled(true);
                    if (e.getCurrentItem()!=null){
                        BuyMenu.openMenu(player, e.getCurrentItem().getType().toString(), file);
                    }
                }
            }
        }

        //Check for checkout
        if (e.getView().getTitle().equals(SellMenuTitle)){
            e.setCancelled(true); // Locks items
        }

    }
}