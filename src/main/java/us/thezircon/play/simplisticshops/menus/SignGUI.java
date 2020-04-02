package us.thezircon.play.simplisticshops.menus;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.utils.SignMenuFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SignGUI {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);

    private static int amt = 0;

    private static void set(int a){
        amt = a;
    }

    public static void sendSignGUI(Player target, File file, String material) {

        SignMenuFactory signMenuFactory = plugin.getSignMenuFactory();

        signMenuFactory
                .newMenu(Lists.newArrayList("###", "^^^^^^^^", "Please Enter", "Custom Amount"))
                .reopenIfFail()
                .response((player, lines) -> {
                    if (lines[0].equals("test")){
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