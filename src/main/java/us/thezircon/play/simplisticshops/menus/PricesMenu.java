package us.thezircon.play.simplisticshops.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class PricesMenu {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    private static DecimalFormat f = new DecimalFormat("#0.00");

    final File Shops = new File(plugin.getDataFolder(), "Shops");
    final File Buy = new File(Shops,"Buy");
    final File Sell = new File(Shops,"Sell");

    public static void open(Player player, File file) {
        YamlConfiguration Shop = YamlConfiguration.loadConfiguration(file);
        String PricesMenuTitle = ChatColor.translateAlternateColorCodes('&', "&6Prices:");
        Inventory gui = Bukkit.createInventory(player, 54, PricesMenuTitle);

        for (String key : Shop.getKeys(true)) {
            if (!(key.contains("Shop") || key.equals("Prices"))) {
                key = key.replace("Prices.", "");

                ItemStack item = new ItemStack(Material.valueOf(key));
                ItemMeta meta = item.getItemMeta();

                List<String> lore = Arrays.asList("", "&7Price @ 1x &6$"+f.format(Shop.getDouble("Prices."+key)), "&7Price @ 64x &6$"+f.format(Shop.getDouble("Prices."+key)*64), "");
                List<String> coloredLore = new ArrayList<>();
                for (String string : lore) {
                    coloredLore.add(ChatColor.translateAlternateColorCodes('&', string));
                }

                meta.setLore(coloredLore);
                item.setItemMeta(meta);

                gui.addItem(item);
            }
        }

        player.openInventory(gui);
    }
}