package us.thezircon.play.simplisticshops.commands.Simplistic.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.commands.cmdManager;
import us.thezircon.play.simplisticshops.utils.Creator;

import java.util.Arrays;
import java.util.List;

public class create extends cmdManager {

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

        if (sender.hasPermission("simplisticshops.createshop")) {
            Creator.createStore(sender, args[1]);
        } else {
            sender.sendMessage("You don't have perm to do this!"); ///////////////////////////// LANG.YML
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
