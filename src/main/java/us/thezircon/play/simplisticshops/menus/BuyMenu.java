package us.thezircon.play.simplisticshops.menus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.error.YAMLException;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class BuyMenu {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    private static DecimalFormat f = new DecimalFormat("#0.00");

    private static String SellMenuTitle = ChatColor.translateAlternateColorCodes('&', "&9Checkout.....");

    private static ItemStack cancelIcon;

    public static void openMenu(Player player, String material, File file){

        FileConfiguration Shop = YamlConfiguration.loadConfiguration(file);

        int size = (9*3);

        Inventory gui = Bukkit.createInventory(player, size, SellMenuTitle);

        //Set Custom Amt
        ItemStack cusamtIcon = new ItemStack(Material.ANVIL);
        ItemMeta cusamt_meta = cusamtIcon.getItemMeta();
        cusamt_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Set Custom Amount"));

        cusamtIcon.setItemMeta(cusamt_meta);

        gui.setItem(10, cusamtIcon);

        //Cancel Item
        cancelIcon = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel_meta = cancelIcon.getItemMeta();
        cancel_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lCANCEL"));
        cancelIcon.setItemMeta(cancel_meta);

        gui.setItem(12, cancelIcon);

        //Item For Sale
        ItemStack sellingIcon = new ItemStack(Material.valueOf(material));
        ItemMeta selling_meta = sellingIcon.getItemMeta();

        List<String> sellLore = Arrays.asList("", "&3Amount: &b{amt}", "&3Total: &b${totalPrice} &3@ &b${price}&3/Per", "");
        List<String> sellLore2 = new ArrayList<>();
        for (String string : sellLore) {
            string = string.replace("{price}", f.format(Shop.getDouble("Prices."+material)));
            string = string.replace("{totalPrice}", f.format((Shop.getDouble("Prices."+material))));
            sellLore2.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, string)));
        }
        selling_meta.setLore(sellLore2);
        sellingIcon.setItemMeta(selling_meta);

        gui.setItem(13, sellingIcon);

        //Buy Item
        ItemStack buyIcon = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta buy_meta = cancelIcon.getItemMeta();
        buy_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lBUY"));

        List<String> buyLore = Arrays.asList("&b{amt} &3@ &b${price}&3/Per", "&3Total: &b${totalPrice}");
        List<String> buyLore2 = new ArrayList<>();
        for (String string : buyLore) {
            string = string.replace("{price}", f.format(Shop.getDouble("Prices."+material)));
            string = string.replace("{totalPrice}", f.format((Shop.getDouble("Prices."+material))));
            buyLore2.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, string)));
        }
        buy_meta.setLore(buyLore2);

        buyIcon.setItemMeta(buy_meta);

        gui.setItem(14, buyIcon);

        //Amt Selector
        ItemStack amountIcon = new ItemStack(Material.DIAMOND);
        ItemMeta amount_meta = amountIcon.getItemMeta();

        amount_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Select Amount:"));

        amountIcon.setItemMeta(amount_meta);

        gui.setItem(16, amountIcon);

        player.openInventory(gui);
    }

    public static String getTitle(){
        return SellMenuTitle;
    }

    public static ItemStack getCancelIcon(){
        return cancelIcon;
    }

}