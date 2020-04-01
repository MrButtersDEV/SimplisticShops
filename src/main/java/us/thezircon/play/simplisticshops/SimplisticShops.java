package us.thezircon.play.simplisticshops;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import us.thezircon.play.simplisticshops.commands.Sell;
import us.thezircon.play.simplisticshops.commands.SellAll;
import us.thezircon.play.simplisticshops.commands.Shop;
import us.thezircon.play.simplisticshops.commands.Simplistic.Simplistic;
import us.thezircon.play.simplisticshops.events.eventClickInventory;
import us.thezircon.play.simplisticshops.events.eventCloseInventory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public final class SimplisticShops extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Setup Economy
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }

        //Setup Directories & Defaults
        getConfig().options().copyDefaults();
        saveDefaultConfig(); // Saves Config
        setupFileStructure(); // Creates Shops File Structure.

        //Events
        getServer().getPluginManager().registerEvents(new eventCloseInventory(), this);
        getServer().getPluginManager().registerEvents(new eventClickInventory(), this);

        //Commands
        getCommand("sellall").setExecutor(new SellAll());
        getCommand("sell").setExecutor(new Sell());
        getCommand("simplistic").setExecutor(new Simplistic());
        getCommand("shop").setExecutor(new Shop());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setupFileStructure() {
        File Shops = new File(this.getDataFolder(), "Shops");
        File Buy = new File(Shops,"Buy");
        File Sell = new File(Shops,"Sell");

        if (!Buy.exists()) {
            Buy.mkdirs();
        }

        if (!Sell.exists()) {
            Sell.mkdirs();
        }

        File buyTemplate = new File(Buy, "buyTemplate.yml");
        InputStream template = getResource("buyTemplate.yml");
        if (!buyTemplate.exists()) {
            Path path = Paths.get(Buy+File.separator+ "buyTemplate.yml");
            try {
                Files.copy(template, path);
            } catch (IOException e) {
                log.warning(String.format("[%s] - Issue Saving templaye.yml for BUY", getDescription().getName()));
            }
        }

        File sellTemplate = new File(Sell, "sellTemplate.yml");
        if (!sellTemplate.exists()) {
            Path path = Paths.get(Sell+File.separator+ "sellTemplate.yml");
            try {
                Files.copy(template, path);
            } catch (IOException e) {
                log.warning(String.format("[%s] - Issue Saving templaye.yml for SELL", getDescription().getName()));
            }
        }

    }

    /* Vault Dependency */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
