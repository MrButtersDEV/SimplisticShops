package us.thezircon.play.simplisticshops.utils;

import com.sun.deploy.panel.ITreeNode;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.io.File;
import java.security.PrivateKey;
import java.util.logging.Logger;

public class Seller {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");
    private static final Economy econ = SimplisticShops.getEconomy();

    public static void sellItems(Player player, Inventory inv, boolean chkReturns){

        final File Shops = new File(plugin.getDataFolder(), "Shops");
        final File Buy = new File(Shops,"Buy");
        final File Sell = new File(Shops,"Sell");

        //Inventory inv = player.getInventory();

        //Get Files in SELL
        File[] sellFiles = Sell.listFiles();
        //Check if player has SimplisticShops.sell.FILE
        try {
            for (File file : sellFiles) {
                if (file.isFile()) {
                    if (player.hasPermission("SimplisticShops.sell." + file.getName())) { // Player Has Shop to Sell to

                        //Sell items:
                        FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
                        double totalSale = 0;
                        int itemsSold = 0;
                        boolean didSell = false;
                        for (int i = 0; i < inv.getStorageContents().length; i++) {
                            ItemStack item = inv.getItem(i);
                            if (item != null) {
                                int amt = item.getAmount();
                                double pricePer = shop.getDouble("Prices." + item.getType().toString());

                                totalSale += (amt*pricePer);

                                if (!new ItemStack(Material.valueOf(item.getType().toString())).getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName().toUpperCase().replace(" ", "_"))){
                                    if (chkReturns) {
                                        returnNonSellables(player, item, shop);
                                    }
                                    continue;
                                }

                                if (shop.getKeys(true).contains("Prices." + item.getType().toString())){
                                    itemsSold += amt;
                                    item.setAmount(0);
                                    didSell = true;
                                } else {
                                    if (chkReturns) {
                                        returnNonSellables(player, item, shop);
                                    }
                                }

                            }
                        }

                        EconomyResponse r = econ.depositPlayer(player, totalSale);
                        if (didSell) {
                            if (r.transactionSuccess()) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&a# &7You sold &e%s&7 item(s) for &6%s&7 and you now have &6%s&7.", itemsSold, econ.format(r.amount), econ.format(r.balance))));
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e# &7Transaction failed."));
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e# &7No items were sold as none were sellable."));
                        }
                    } else { // NO File / Perm Found ----------------- If using chest it will dupe
                        if (chkReturns) { // Sell GUI
                            for (ItemStack items : inv.getContents()) {
                                if (items!=null) {
                                    player.getInventory().addItem(items);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c# &7No items were sold as you have no shop to sell them too."));
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException err) {
            log.info(String.format("[%s] - An error occurred while attempting to sell items.", plugin.getDescription().getName())); // No Shops to sell to
            err.printStackTrace();
        }

    }

    private static void returnNonSellables(Player player, ItemStack returnable, FileConfiguration shop) {
        if (!shop.getKeys(true).contains(returnable.getType().toString())) {
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(returnable);
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), returnable);
            }
        }
    }


}
