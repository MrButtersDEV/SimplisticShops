package us.thezircon.play.simplisticshops.menus;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.utils.SignMenuFactory;

import java.io.File;

public class SignGUI {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);

    public static void sendSignGUI(Player target, File file, String material) {

        SignMenuFactory signMenuFactory = plugin.getSignMenuFactory();

        signMenuFactory
                .newMenu(Lists.newArrayList("###", "^^^^^^^^", "Please Enter", "Custom Amount"))
                .reopenIfFail()
                .response((player, lines) -> {
                    if (isNumeric(lines[0])){
                        return true;
                    } else {
                        return false;
                    }
                })
                .open(target);
    }

    private static boolean isNumeric(final String str) {
        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }

}