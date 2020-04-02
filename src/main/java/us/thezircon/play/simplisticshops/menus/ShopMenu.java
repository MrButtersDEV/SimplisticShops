package us.thezircon.play.simplisticshops.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Objects;

public class ShopMenu {

    public static void openShop(Player player, File file){

        FileConfiguration shop = YamlConfiguration.loadConfiguration(file);
        String ShopTitle = ChatColor.translateAlternateColorCodes('&', shop.getString("Shop.DisplayName"));

        DecimalFormat f = new DecimalFormat("#0.00");

        Inventory gui = Bukkit.createInventory(player, 54, ShopTitle);

        for (String keys : shop.getKeys(true)) {

            if (keys.contains("Shop") || keys.equals("Prices")) {
                continue;
            }

            keys = keys.replace("Prices.", "");

            ItemStack item = new ItemStack(Material.valueOf(keys));

            String name = item.getType().toString().replace("_", " ");
            String words[] = name.split(" ");

            StringBuilder name2 = new StringBuilder();
            for (String w : words) {
                String first = w.substring(0, 1);
                String after = w.substring(1);
                name2.append(first.toUpperCase()).append(after.toLowerCase()).append(" ");
            }

            String itemName = ChatColor.translateAlternateColorCodes('&', ("&3"+ name2 + "&a$"+ f.format(shop.getDouble("Prices." + keys)) +"/Per"));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(itemName);
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);

    }
}
