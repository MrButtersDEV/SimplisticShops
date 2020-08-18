package us.thezircon.play.simplisticshops.utils.menu;

import org.bukkit.entity.Player;

import java.io.File;

/*
Companion class to all menus. This is needed to pass information across the entire
 menu system no matter how many inventories are opened or closed.

 Each player has one of these objects, and only one.
 */

public class PlayerMenuUtility {


    private Player owner;
    private File file;
    //store the player that will be killed so we can access him in the next menu

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public PlayerMenuUtility(Player p, File f) {
        this.owner = p;
        this.file = f;
    }

    public File getFile() {
        return file;
    }

    public Player getOwner() {
        return owner;
    }

}
