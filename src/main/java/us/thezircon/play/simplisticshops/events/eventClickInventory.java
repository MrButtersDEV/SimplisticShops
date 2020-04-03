package us.thezircon.play.simplisticshops.events;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.menus.BuyMenu;
import us.thezircon.play.simplisticshops.menus.ShopMenu;
import us.thezircon.play.simplisticshops.menus.SignGUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class eventClickInventory implements Listener {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");
    private static final Economy econ = SimplisticShops.getEconomy();

    private static final File Shops = new File(plugin.getDataFolder(), "Shops");
    private static final File Buy = new File(Shops,"Buy");
    private static final File Sell = new File(Shops,"Sell");

    private static Sound menuSaleCompleteSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuSaleCompleteSound"));
    private static Sound menuSaleFailedSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuSaleFailedSound"));

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        String SellShopsMenuTitle = ChatColor.translateAlternateColorCodes('&', "&3Shops");

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
                        BuyMenu.openMenu(player, e.getCurrentItem().getType().toString(), file, 1);
                    }
                }
            }
        }

        //Check for checkout
        if (e.getView().getTitle().equals(BuyMenu.getTitle())){
            e.setCancelled(true); // Locks items

            if (e.getCurrentItem()==null) {
                return;
            }

            if (e.getCurrentItem().equals(BuyMenu.getCancelIcon())){ // Cancel Icon
                player.closeInventory();
            } else if (e.getCurrentItem().equals(BuyMenu.getAmountIcon())){ // Amount changer
                if (buyFiles != null) {
                    if (BuyMenu.getAmount()<128) {
                        BuyMenu.openMenu(player, BuyMenu.getsellingIcon().getType().toString(), BuyMenu.getFile(), BuyMenu.getAmount() * 2);
                    } else {
                        BuyMenu.openMenu(player, BuyMenu.getsellingIcon().getType().toString(), BuyMenu.getFile(), 1);
                    }
                }
            } else if (e.getCurrentItem().equals(BuyMenu.getcusamtIcon())){
                player.closeInventory();
                if (buyFiles != null) {
                    for (File file : buyFiles) {
                        if (e.getView().getTitle().equals(BuyMenu.getTitle())) { // May get wrong file idk????????
                            //try {
                            //    TimeUnit.SECONDS.sleep(1);
                            //} catch (InterruptedException ignored) {}
                            SignGUI.sendSignGUI(player, file, BuyMenu.getsellingIcon().getType().toString());
                        }
                    }
                }
            } else if (e.getCurrentItem().equals(BuyMenu.getBuyIcon())) {

                double price = BuyMenu.getTotalPrice();
                EconomyResponse econRES = econ.withdrawPlayer(player, price);
                if (econRES.transactionSuccess()) {
                    ItemStack item = new ItemStack(BuyMenu.getsellingIcon().getType(), BuyMenu.getAmount());
                    player.getInventory().addItem(item);
                    player.closeInventory();

                    String name = item.getType().toString().replace("_", " ");
                    String words[] = name.split(" ");

                    StringBuilder name2 = new StringBuilder();
                    for (String w : words) {
                        String first = w.substring(0, 1);
                        String after = w.substring(1);
                        name2.append(first.toUpperCase()).append(after.toLowerCase()).append(" ");
                    }

                    player.sendMessage("You bought " + item.getAmount() + " of " + name2 + " for $" + price);
                    player.playSound(player.getLocation(), menuSaleCompleteSound, 3, 1);

                } else {
                    player.sendMessage("Transaction failed!");
                    player.playSound(player.getLocation(), menuSaleFailedSound, 3, 1);
                }
            }
        }

    }
}