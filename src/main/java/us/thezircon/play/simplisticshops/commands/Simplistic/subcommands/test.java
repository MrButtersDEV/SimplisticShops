package us.thezircon.play.simplisticshops.commands.Simplistic.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.commands.cmdManager;
import us.thezircon.play.simplisticshops.utils.anvilSetCustomAmount;

import java.util.List;

public class test extends cmdManager {
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
        anvilSetCustomAmount.open((Player) sender);
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
