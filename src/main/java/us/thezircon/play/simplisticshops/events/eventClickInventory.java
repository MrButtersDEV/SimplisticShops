package us.thezircon.play.simplisticshops.events;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.menus.BuyMenu;
import us.thezircon.play.simplisticshops.menus.ShopMenu;
import us.thezircon.play.simplisticshops.menus.AmountMenu;

import javax.xml.bind.helpers.DefaultValidationEventHandler;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class eventClickInventory implements Listener {

    private final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private final Logger log = Logger.getLogger("Minecraft");
    private final Economy econ = SimplisticShops.getEconomy();
    private final File Shops = new File(plugin.getDataFolder(), "Shops");
    private final File Buy = new File(Shops,"Buy");
    private final File Sell = new File(Shops,"Sell");
    private Sound menuSaleCompleteSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuSaleCompleteSound"));
    private Sound menuSaleFailedSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuSaleFailedSound"));
    private Sound menuOpenSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuOpenSound"));

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        String SellShopsMenuTitle = ChatColor.translateAlternateColorCodes('&', "&3Shops");

        Player player = (Player) e.getWhoClicked();
        DecimalFormat f = new DecimalFormat("#0.00");

        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }

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
                        //BuyMenu.openMenu(player, e.getCurrentItem().getType().toString(), file, 1); OLD SYSTEM
                        BuyMenu checkout = new BuyMenu(player, e.getCurrentItem().getType().toString(), file, 1);
                        plugin.hmChkOut.put(player, checkout);
                        checkout.open();
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

            BuyMenu checkout = plugin.hmChkOut.get(player);

            if (e.getCurrentItem().equals(checkout.getCancelIcon())){ // Cancel Icon
                player.closeInventory();
                plugin.hmChkOut.remove(player);
            } else if (e.getCurrentItem().equals(checkout.getAmountIcon())){ // Amount changer
                List doDontNum = Arrays.asList(1, 2, 4, 8, 16, 32, 64, 128);
                player.playSound(player.getLocation(), menuOpenSound, 1, 1);
                if (checkout.getAmount()<128 && doDontNum.contains(checkout.getAmount())) {
                    //checkout.openMenu(player, checkout.getForSaleIcon().getType().toString(), checkout.getFile(), checkout.getAmount() * 2);
                    checkout.setNewAmount(checkout.getAmount() * 2);
                    plugin.hmChkOut.put(player, checkout);
                    player.updateInventory();
                } else {
                    //checkout.openMenu(player, checkout.getForSaleIcon().getType().toString(), checkout.getFile(), 1);
                    checkout.setNewAmount(1);
                    plugin.hmChkOut.put(player, checkout);
                    player.updateInventory();
                }
            } else if (e.getCurrentItem().equals(checkout.getCustomAmtIcon())){
                player.closeInventory();
                if (buyFiles != null) {
                    for (File file : buyFiles) {
                        if (e.getView().getTitle().equals(BuyMenu.getTitle())) { // May get wrong file idk????????
                            AmountMenu.openAnvil(player, file, checkout.getForSaleIcon().getType().toString());
                            break;
                        }
                    }
                }
            } else if (e.getCurrentItem().equals(checkout.getBuyIcon())) {
                double price = checkout.getTotalPrice();
                EconomyResponse econRES = econ.withdrawPlayer(player, price);
                if (econRES.transactionSuccess()) {
                    ItemStack item = new ItemStack(checkout.getForSaleIcon().getType(), checkout.getAmount());
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

                    String saleComplete = ChatColor.translateAlternateColorCodes('&', "&a# &7You bought &b{amount} &7of &3{item} &7for &a${price}".replace("{amount}", item.getAmount()+"").replace("{item}", name2).replace("  ", " ").replace("{price}", f.format(price)));

                    player.sendMessage(saleComplete);
                    player.playSound(player.getLocation(), menuSaleCompleteSound, 3, 1);
                    plugin.hmChkOut.remove(player);
                } else {
                    String saleFail = ChatColor.translateAlternateColorCodes('&', "&c#&7 Transaction Failed. Check your balance!");
                    player.sendMessage(saleFail);
                    player.playSound(player.getLocation(), menuSaleFailedSound, 3, 1);
                }
            }
        }

    }
}