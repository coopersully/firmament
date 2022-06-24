package me.coopersully.Firmament;

import me.coopersully.Firmament.commands.CommandFirmament;
import me.coopersully.Firmament.commands.CommandMaximum;
import me.coopersully.Firmament.commands.CommandMinimum;
import me.coopersully.Firmament.commands.CommandSacrifice;
import me.coopersully.Firmament.events.FDeathEvent;
import me.coopersully.Firmament.events.FLevelChangeEvent;
import me.coopersully.Firmament.events.FJoinEvent;
import me.coopersully.Firmament.events.FQuitEvent;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class FirmamentPlugin extends JavaPlugin {

    private static FirmamentPlugin instance;

    public static Firmament worldBorder;

    public static String settingsNamespace = "world";
    public static int settingsMultiplier = 0;
    public static boolean settingsAnnouncements = true;

    public static boolean sacrificeEnabled = true;
    public static int sacrificePercentage = 10;

    public static int permanentBlocks = 0;

    public static List<ItemStack> guaranteedItems;
    public static List<ItemStack> possibleItems;


    public void onEnable() {

        // Initialize instance
        instance = this;

        // Enable all listeners
        getServer().getPluginManager().registerEvents(new FLevelChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new FJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new FQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new FDeathEvent(), this);

        // Generate config
        saveDefaultConfig();
        reloadDefaultConfig();

        // Initialize borders
        if (getServer().getWorld(settingsNamespace) == null) {
            System.out.println(ChatColor.RED + "No worlds by the name of \"" + settingsNamespace + "\" were found; please check if your config.yml is correctly configured.");
            System.out.println(ChatColor.RED + "Disabling the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        // Initialize "worldBorder" with name of world
        worldBorder = new Firmament(settingsNamespace);
        worldBorder.refresh(true, true);

        // Register all commands
        getServer().getPluginCommand("firmament").setExecutor(new CommandFirmament());
        getServer().getPluginCommand("sacrifice").setExecutor(new CommandSacrifice());
        getServer().getPluginCommand("minimum").setExecutor(new CommandMinimum());
        getServer().getPluginCommand("maximum").setExecutor(new CommandMaximum());
    }

    public void onDisable() { }

    public static FirmamentPlugin getInstance() {
        return instance;
    }
    
    public static void reloadDefaultConfig() {
        getInstance().reloadConfig();
        getInstance().saveDefaultConfig();

        // Settings section
        settingsNamespace = getInstance().getConfig().getString("settings.namespace");
        settingsMultiplier = getInstance().getConfig().getInt("settings.multiplier");
        settingsAnnouncements = getInstance().getConfig().getBoolean("settings.announcements");

        // Sacrifice section
        sacrificeEnabled = getInstance().getConfig().getBoolean("sacrifice.enabled");
        sacrificePercentage = getInstance().getConfig().getInt("sacrifice.percentage");

        // Storage section
        permanentBlocks = getInstance().getConfig().getInt("permanent-blocks");

        // Join items section
        var startingItems = getInstance().getConfig().getConfigurationSection("starting-items");
        guaranteedItems = CoreUtils.getItemsFromStrings(startingItems.getStringList("guaranteed"));
        possibleItems = CoreUtils.getItemsFromStrings(startingItems.getStringList("possibilities"));
    }

}
