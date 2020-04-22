package us.thezircon.play.simplisticshops.commands.SetPrice.subcommands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.commands.cmdManager;
import us.thezircon.play.simplisticshops.menus.BuyMenu;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class buy extends cmdManager {

    private final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private final Logger log = Logger.getLogger("Minecraft");

    private final File Shops = new File(plugin.getDataFolder(), "Shops");
    private final File Buy = new File(Shops,"Buy");

    private List<String> materials = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList());

    @Override
    public String getName() {
        return "buy";
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
        File file = new File(Buy, args[1]);
        FileConfiguration store = YamlConfiguration.loadConfiguration(file);

        if (!materials.contains(args[2].toUpperCase())) {
            sender.sendMessage(args[2] + "is not a valid material!");
            return;
        }

        store.set("Prices."+args[2].toUpperCase(), Double.valueOf(args[3]));
        try {
            store.save(file);
            sender.sendMessage("Price set!");
        } catch (IOException e) {
            sender.sendMessage("Unable to set price!");
        }
    }

    @Override
    public List<String> arg1(Player player, String[] args) {
        ArrayList<String> buyFiles = new ArrayList<>();
        for (File files : Objects.requireNonNull(Buy.listFiles())) {
            buyFiles.add(files.getName());
        }
        return buyFiles;
    }

    @Override
    public List<String> arg2(Player player, String[] args) {
        return materials;
    }

    @Override
    public List<String> arg3(Player player, String[] args) {
        return Collections.singletonList("00.00");
    }
}
