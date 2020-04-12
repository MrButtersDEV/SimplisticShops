package us.thezircon.play.simplisticshops.menus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Shops {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    private static final File Shops = new File(plugin.getDataFolder(), "Shops");
    private static final File Buy = new File(Shops,"Buy");

    private static Sound saleCompleteSound = Sound.valueOf(plugin.getConfig().getString("SellSettings.Sounds.sellSound"));

    public static void openMenu(Player player) {

        String SellMenuTitle = ChatColor.translateAlternateColorCodes('&', "&3Shops");
        int size = (9*3);

        player.playSound(player.getLocation(), saleCompleteSound, 3, 1);

        Inventory gui = Bukkit.createInventory(player, size, SellMenuTitle);
        try {
        //Get Files in SELL
        File[] buyFiles = Buy.listFiles();
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

                if (store.contains("Shop.Slot", true)) {
                    int slot = store.getInt("Shop.Slot");
                    gui.setItem(slot, icon);
                } else {
                    gui.addItem(icon);
                }

            }
        } catch (NullPointerException err) {
            player.sendMessage("No stores found!");
        }
        //Check if player has SimplisticShops.sell.FILE
        player.openInventory(gui);
    }
}
