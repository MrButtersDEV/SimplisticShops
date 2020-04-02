package us.thezircon.play.simplisticshops.utils;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.util.logging.Logger;

public class anvilSetCustomAmount {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    public static void open(Player p){
        new AnvilGUI.Builder()
                .onClose(player -> {                      //called when the inventory is closing
            player.sendMessage("You closed the inventory.");
        })
                .onComplete((player, text) -> {           //called when the inventory output slot is clicked
            if(text.equalsIgnoreCase("you")) {
                player.sendMessage("You have magical powers!");
                return AnvilGUI.Response.close();
            } else {
                return AnvilGUI.Response.text("Incorrect.");
            }
        })
                .preventClose()                           //prevents the inventory from being closed
        .text("What is the meaning of life?")     //sets the text the GUI should start with
        .item(new ItemStack(Material.GOLD_BLOCK)) //use a custom item for the first slot
                .title("Enter your answer.")              //set the title of the GUI (only works in 1.14+)
        .plugin(plugin)                 //set the plugin instance
        .open(p);                          //opens the GUI for the player provided
    }
}
