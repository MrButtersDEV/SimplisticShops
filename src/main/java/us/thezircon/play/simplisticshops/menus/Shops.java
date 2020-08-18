package us.thezircon.play.simplisticshops.menus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.utils.menu.Menu;
import us.thezircon.play.simplisticshops.utils.menu.PlayerMenuUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Shops extends Menu {

    private static final SimplisticShops PLUGIN = SimplisticShops.getPlugin(SimplisticShops.class);

    public Shops(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&3Shops");
    }

    @Override
    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void handleMenu(InventoryClickEvent e) {
        File Shops = new File(PLUGIN.getDataFolder(), "Shops");
        File Buy = new File(Shops,"Buy");
        Player player = (Player) e.getWhoClicked();
        NamespacedKey keyShopFileName = new NamespacedKey(PLUGIN, "shopFileName");
        PersistentDataContainer container = e.getCurrentItem().getItemMeta().getPersistentDataContainer();
        File file = null;
        if (container.has(keyShopFileName, PersistentDataType.STRING)) {
            file = new File(Buy, container.get(keyShopFileName, PersistentDataType.STRING));
            new ShopMenu(new PlayerMenuUtility(player, file));
        }
    }

    @Override
    public void setMenuItems() {
        Player player = playerMenuUtility.getOwner();
        File Shops = new File(PLUGIN.getDataFolder(), "Shops");
        File Buy = new File(Shops,"Buy");

        //Get Shops
        try {
            File[] buyFiles = Buy.listFiles(); // Get shops players can buy from
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

                NamespacedKey keyShopFileName = new NamespacedKey(PLUGIN, "shopFileName");
                meta.getPersistentDataContainer().set(keyShopFileName, PersistentDataType.STRING, file.getName());

                meta.setDisplayName(name);
                meta.setLore(lore);
                icon.setItemMeta(meta);

                if (store.contains("Shop.Slot", true)) {
                    int slot = store.getInt("Shop.Slot");
                    inventory.setItem(slot, icon);
                } else {
                    inventory.addItem(icon);
                }
            }
        } catch (NullPointerException err) {
            player.sendMessage("No stores found!");
        }
    }
}
