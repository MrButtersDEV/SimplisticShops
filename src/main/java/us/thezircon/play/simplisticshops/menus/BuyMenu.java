package us.thezircon.play.simplisticshops.menus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
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

    private static int amt = 0;

    private static Sound menuOpenSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuOpenSound"));

    private static double totalPrice = 0.00;

    private static ItemStack cancelIcon,amountIcon,sellingIcon,buyIcon,cusamtIcon;

    private static File returnFile;

    public static void openMenu(Player player, String material, File file, int amount){

        returnFile = file;

        amt = amount;

        FileConfiguration Shop = YamlConfiguration.loadConfiguration(file);

        player.playSound(player.getLocation(), menuOpenSound, 3, 1);

        totalPrice = Shop.getDouble("Prices."+material);

        int size = (9*3);

        Inventory gui = Bukkit.createInventory(player, size, SellMenuTitle);

        //Set Custom Amt
        cusamtIcon = new ItemStack(Material.OAK_SIGN);
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

        //Item For Sale --------------------------------------------------------------------------------------------------------
        sellingIcon = new ItemStack(Material.valueOf(material));
        ItemMeta selling_meta = sellingIcon.getItemMeta();

        List<String> sellLore = Arrays.asList("", "&3Amount: &b{amt}", "&3Total: &b${totalPrice} &3@ &b${price}&3/Per", "");
        List<String> sellLore2 = new ArrayList<>();
        for (String string : sellLore) {
            string = string.replace("{amt}", amount+"");
            string = string.replace("{price}", f.format(Shop.getDouble("Prices."+material)));
            string = string.replace("{totalPrice}", f.format((Shop.getDouble("Prices."+material)*amount)));
            sellLore2.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, string)));
        }
        selling_meta.setLore(sellLore2);
        sellingIcon.setItemMeta(selling_meta);

        gui.setItem(13, sellingIcon);

        //Buy Item
        buyIcon = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta buy_meta = cancelIcon.getItemMeta();
        buy_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lBUY"));

        List<String> buyLore = Arrays.asList("&b{amt} &3@ &b${price}&3/Per", "&3Total: &b${totalPrice}");
        List<String> buyLore2 = new ArrayList<>();
        for (String string : buyLore) {
            string = string.replace("{amt}", amount+"");
            string = string.replace("{price}", f.format(Shop.getDouble("Prices."+material)));
            string = string.replace("{totalPrice}", f.format((Shop.getDouble("Prices."+material)*amount)));
            totalPrice = ((Shop.getDouble("Prices."+material)*amount));
            buyLore2.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, string)));
        }
        buy_meta.setLore(buyLore2);

        buyIcon.setItemMeta(buy_meta);

        gui.setItem(14, buyIcon);

        //Amt Selector
        amountIcon = new ItemStack(Material.DIAMOND);
        ItemMeta amount_meta = amountIcon.getItemMeta();

        amount_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Select Amount: &b" + amount));

        amountIcon.setItemMeta(amount_meta);

        gui.setItem(16, amountIcon);

        player.openInventory(gui);
    }

    public static String getTitle(){
        return SellMenuTitle;
    }

    public static double getTotalPrice(){
        return totalPrice;
    }

    public static int getAmount(){
        return amt;
    }

    public static ItemStack getsellingIcon(){
        return sellingIcon;
    }

    public static ItemStack getcusamtIcon() {
        return cusamtIcon;
    }

    public static ItemStack getBuyIcon(){
        return buyIcon;
    }

    public static ItemStack getCancelIcon(){
        return cancelIcon;
    }

    public static ItemStack getAmountIcon(){
        return amountIcon;
    }

    public static File getFile(){
        return returnFile;
    }

}