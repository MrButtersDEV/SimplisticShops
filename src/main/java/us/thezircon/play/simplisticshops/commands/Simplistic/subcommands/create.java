package us.thezircon.play.simplisticshops.commands.Simplistic.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.commands.cmdManager;
import us.thezircon.play.simplisticshops.utils.Creator;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class create extends cmdManager {

    private final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private final Logger log = Logger.getLogger("Minecraft");

    @Override
    public String getName() {
        return "create";
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

        String msgPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgPrefix"));
        String msgNoPerms = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgNoPerm"));

        if (sender.hasPermission("simplisticshops.createshop")) {
            Creator.createStore(sender, args[1]);
        } else {
            sender.sendMessage(msgPrefix + " " + msgNoPerms);
        }

    }

    @Override
    public List<String> arg1(Player player, String[] args) {
        List<String> exampleShopName = Arrays.asList( "ShopName.yml", "BuildingBlocks.yml", "DecorationBlocks.yml", "Redstone.yml", "Trasnsportation.yml", "Miscellaneous.yml", "Foodstuffs.yml", "Tools.yml", "Combat.yml", "Brewing.yml");
        return exampleShopName;
    }

    @Override
    public List<String> arg2(Player player, String[] args) {
        return null;
    }
}
