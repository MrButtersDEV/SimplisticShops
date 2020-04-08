package us.thezircon.play.simplisticshops.commands.Simplistic.subcommands;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.commands.cmdManager;

import java.util.List;
import java.util.logging.Logger;

public class test extends cmdManager {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            new AnvilGUI.Builder()
                    .onClose(player -> {                      //called when the inventory is closing
                        player.sendMessage("You closed the inventory.");
                    })
                    .onComplete((player, text) -> {           //called when the inventory output slot is clicked
                        if (isNumeric(text)) {
                            player.sendMessage("You have magical # powers!");
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
                    .open(p);

        }
    }

    @Override
    public List<String> arg1(Player player, String[] args) {
        return null;
    }

    @Override
    public List<String> arg2(Player player, String[] args) {
        return null;
    }

    @Override
    public List<String> arg3(Player player, String[] args) {
        return null;
    }

    private static boolean isNumeric(final String str) {
        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

}
