package us.thezircon.play.simplisticshops.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.error.YAMLException;
import us.thezircon.play.simplisticshops.SimplisticShops;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Creator {

    private static final SimplisticShops plugin = SimplisticShops.getPlugin(SimplisticShops.class);
    private static final Logger log = Logger.getLogger("Minecraft");

    private static final File Shops = new File(plugin.getDataFolder(), "Shops");
    private static final File Buy = new File(Shops,"Buy");
    private static final File Sell = new File(Shops,"Sell");

    public static void createBuyStore(CommandSender sender, String shopName){ // Creates a new shop
        if (!shopName.endsWith(".yml")) {
            shopName = shopName+".yml";
        }

        File shopFile = new File(Buy, shopName);
        if (!shopFile.exists()) {
            try {
                shopFile.createNewFile();

                Path path = Paths.get(Buy+File.separator+shopName);
                InputStream template = plugin.getResource("buyTemplate.yml");

                byte[] buffer = new byte[template.available()];
                template.read(buffer);

                OutputStream outStream = new FileOutputStream(shopFile);
                outStream.write(buffer);

                FileConfiguration shop = YamlConfiguration.loadConfiguration(shopFile);
                shop.set("Shop.DisplayName", "&3"+shopName.replace(".yml", " Shop"));
                shop.save(shopFile);

                sender.sendMessage("Successfully created shop");
            } catch (IOException e) {
                sender.sendMessage("Unable to create shop "+shopName);
            }
        } else {
            sender.sendMessage("A shop by the name of '"+shopName+"' already exists.");
        }

    }

    public static void createSellShop(CommandSender sender, String shopName){

        if (!shopName.endsWith(".yml")) {
            shopName = shopName+".yml";
        }

        File shopFile = new File(Sell, shopName);
        if (!shopFile.exists()) {
            try {
                shopFile.createNewFile();

                Path path = Paths.get(Sell+File.separator+shopName);
                InputStream template = plugin.getResource("sellTemplate.yml");

                byte[] buffer = new byte[template.available()];
                template.read(buffer);

                OutputStream outStream = new FileOutputStream(shopFile);
                outStream.write(buffer);

                FileConfiguration shop = YamlConfiguration.loadConfiguration(shopFile);
                shop.set("Shop.DisplayName", "&3"+shopName.replace(".yml", " Shop"));
                shop.set("Shop.Name", ""+shopName.replace(".yml", " Shop"));
                shop.save(shopFile);

                sender.sendMessage("Successfully created shop");
            } catch (IOException e) {
                sender.sendMessage("Unable to create shop "+shopName);
            }
        } else {
            sender.sendMessage("A shop by the name of '"+shopName+"' already exists.");
        }
    }
}