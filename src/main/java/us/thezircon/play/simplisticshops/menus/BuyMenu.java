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

    private DecimalFormat f = new DecimalFormat("#0.00");

    private static final String SellMenuTitle = ChatColor.translateAlternateColorCodes('&', "&9Checkout.....");

    private Sound menuOpenSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuOpenSound"));
    private double totalPrice = 0.00;
    private ItemStack cancelIcon,amountIcon,forSaleIcon,buyIcon,cusamtIcon;

    private Player player;
    private String material;
    private File file;
    private int amount = 0;
    private Inventory gui;
    private YamlConfiguration shop;

    public BuyMenu(Player player, String material, File file, int amount){
        this.player = player;
        this.material = material;
        this.file = file;
        this.amount = amount;
        this.shop = YamlConfiguration.loadConfiguration(file);
        gui = Bukkit.createInventory(player, 27, SellMenuTitle);

        setAmountIcon(); // Diamond - Doubles selected amt
        setBuyIcon(); // Green Glass - Buys Items
        setCancelIcon(); // Red Glass - Cancels / Closes menu
        setCustomAmtIcon(); // Sign - Opens Anvil GUI to get custom amt
        setForSaleIcon(); // ???? - The item the player is buying with its info
    }

    public void open() {
        player.openInventory(gui);
        player.playSound(player.getLocation(), menuOpenSound, 1, 1);
    }

    // Custom Amount Icon - Sets custom amt
    public void setCustomAmtIcon() { // Sets item details
        cusamtIcon = new ItemStack(Material.OAK_SIGN);
        ItemMeta cusamt_meta = cusamtIcon.getItemMeta();
        cusamt_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Set Custom Amount"));
        cusamtIcon.setItemMeta(cusamt_meta);

        gui.setItem(10, cusamtIcon);
    }

    public ItemStack getCustomAmtIcon() { // Returns item for checking clicked on.
        return cusamtIcon;
    }

    // Cancel Icon - Closes inv
    public void setCancelIcon() {
        cancelIcon = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancel_meta = cancelIcon.getItemMeta();
        cancel_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lCANCEL"));
        cancelIcon.setItemMeta(cancel_meta);

        gui.setItem(12, cancelIcon);
    }

    public ItemStack getCancelIcon() {
        return cancelIcon;
    }

    // Item For Sale Icon - Displays info about the pending purchase
    public void setForSaleIcon() {
        forSaleIcon = new ItemStack(Material.valueOf(material));
        ItemMeta selling_meta = forSaleIcon.getItemMeta();

        List<String> sellLore = Arrays.asList("", "&3Amount: &b{amt}", "&3Total: &b${totalPrice} &3@ &b${price}&3/Per", "");
        List<String> sellLore2 = new ArrayList<>();
        for (String string : sellLore) {
            string = string.replace("{amt}", amount+"");
            string = string.replace("{price}", f.format(shop.getDouble("Prices."+material)));
            string = string.replace("{totalPrice}", f.format((shop.getDouble("Prices."+material)*amount)));
            totalPrice = ((shop.getDouble("Prices."+material)*amount));
            sellLore2.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, string)));
        }
        selling_meta.setLore(sellLore2);
        forSaleIcon.setItemMeta(selling_meta);

        gui.setItem(13, forSaleIcon);
    }

    public ItemStack getForSaleIcon() {
        return forSaleIcon;
    }

    // Buy Icon - Displays details and buys the item for sale
    public void setBuyIcon() {
        buyIcon = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta buy_meta = buyIcon.getItemMeta();
        buy_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lBUY"));

        List<String> buyLore = Arrays.asList("&b{amt} &3@ &b${price}&3/Per", "&3Total: &b${totalPrice}");
        List<String> buyLore2 = new ArrayList<>();
        for (String string : buyLore) {
            string = string.replace("{amt}", amount+"");
            string = string.replace("{price}", f.format(shop.getDouble("Prices."+material)));
            string = string.replace("{totalPrice}", f.format((shop.getDouble("Prices."+material)*amount)));
            totalPrice = ((shop.getDouble("Prices."+material)*amount));
            buyLore2.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, string)));
        }
        buy_meta.setLore(buyLore2);
        buyIcon.setItemMeta(buy_meta);

        gui.setItem(14, buyIcon);
    }

    public ItemStack getBuyIcon() {
        return buyIcon;
    }

    // Amount Icon - Change amt for sale 1, 2, 4, 8, 16, 64, 128 (Or custom)
    public void setAmountIcon() {
        amountIcon = new ItemStack(Material.DIAMOND);
        ItemMeta amount_meta = amountIcon.getItemMeta();
        amount_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Select Amount: &b" + amount));
        amountIcon.setItemMeta(amount_meta);

        gui.setItem(16, amountIcon);
    }

    public ItemStack getAmountIcon() {
        return amountIcon;
    }

    public void setNewAmount(int amt) {
        this.amount = amt;
        setAmountIcon();
        setForSaleIcon();
        setBuyIcon();
    }

    public int getAmount() {
        return amount;
    }

    public File getFile() {
        return file;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public static String getTitle() {
        return SellMenuTitle;
    }
}