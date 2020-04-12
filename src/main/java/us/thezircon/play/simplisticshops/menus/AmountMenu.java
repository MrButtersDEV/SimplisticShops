package us.thezircon.play.simplisticshops.menus;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.io.File;

public class AmountMenu {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);

    public static void openAnvil(Player p, File file, String material) {

        String name = ChatColor.translateAlternateColorCodes('&', "&3Enter an Amount");
        Sound menuOpenSound = Sound.valueOf(plugin.getConfig().getString("BuySettings.Sounds.menuOpenSound"));

        ItemStack icon = new ItemStack(Material.KNOWLEDGE_BOOK);
        ItemMeta meta = icon.getItemMeta();
        icon.setItemMeta(meta);

        p.playSound(p.getLocation(), menuOpenSound, 1, 1);

        // Make it so it caps so players cant get more items than they can hold

        new AnvilGUI.Builder()
                //.onClose(player -> {                      //called when the inventory is closing
                //    player.sendMessage("You closed the inventory. Please enter a valid number.");
                //})
                .onComplete((player, text) -> {           //called when the inventory output slot is clicked
                    if(isNumeric(text)) {
                        //BuyMenu.openMenu(p, material, file, Integer.parseInt(text)); - OLD SYSTEM
                        BuyMenu checkout = new BuyMenu(p, material, file, Integer.parseInt(text));
                        plugin.hmChkOut.put(p, checkout);
                        checkout.open();
                        return AnvilGUI.Response.close();
                    } else {
                        return AnvilGUI.Response.text("Please enter a number!");
                    }
                })
                //.preventClose()                           //prevents the inventory from being closed
                .text("###")     //sets the text the GUI should start with
                .item(icon) //use a custom item for the first slot
                .title(name)              //set the title of the GUI (only works in 1.14+)
                .plugin(plugin)                 //set the plugin instance
                .open(p);
    }

    private static boolean isNumeric(final String str) {
        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }
}