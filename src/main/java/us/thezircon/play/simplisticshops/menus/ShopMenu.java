package us.thezircon.play.simplisticshops.menus;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import us.thezircon.play.simplisticshops.utils.menu.PaginatedMenu;
import us.thezircon.play.simplisticshops.utils.menu.PlayerMenuUtility;

import java.io.File;

public class ShopMenu extends PaginatedMenu {

    public ShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    private File file = playerMenuUtility.getFile();
    private YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', conf.getString("Shop.DisplayName"));
    }

    @Override
    public InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        for (String keys : )
    }
}
