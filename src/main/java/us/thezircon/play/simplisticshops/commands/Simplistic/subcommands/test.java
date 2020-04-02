package us.thezircon.play.simplisticshops.commands.Simplistic.subcommands;

import com.comphenix.protocol.PacketType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.commands.cmdManager;
import us.thezircon.play.simplisticshops.menus.SignGUI;

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

            Player player = (Player) sender;
            //SignGUI.sendSignGUI(player);

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
}
