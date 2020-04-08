package us.thezircon.play.simplisticshops.commands.SetPrice.subcommands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import us.thezircon.play.simplisticshops.SimplisticShops;
import us.thezircon.play.simplisticshops.commands.cmdManager;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class sell extends cmdManager {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    private static final File Shops = new File(plugin.getDataFolder(), "Shops");
    private static final File Sell = new File(Shops, "Sell");

    private List<String> materials = Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList());

    @Override
    public String getName() {
        return "sell";
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

        File file = new File(Sell, args[1]);
        FileConfiguration store = YamlConfiguration.loadConfiguration(file);

        if (!materials.contains(args[2].toUpperCase())) {
            sender.sendMessage(args[2] + "is not a valid material!");
            return;
        }

        store.set("Prices." + args[2].toUpperCase(), Double.valueOf(args[3]));
        try {
            store.save(file);
            sender.sendMessage("Price set!");
        } catch (IOException e) {
            sender.sendMessage("Unable to set price!");
        }
    }

    @Override
    public List<String> arg1(Player player, String[] args) {
        ArrayList<String> sellFiles = new ArrayList<>();
        for (File files : Objects.requireNonNull(Sell.listFiles())) {
            sellFiles.add(files.getName());
        }
        return sellFiles;
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
