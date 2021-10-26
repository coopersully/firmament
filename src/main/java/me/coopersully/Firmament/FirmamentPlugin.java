package me.coopersully.Firmament;

import me.coopersully.Firmament.events.FDeathEvent;
import me.coopersully.Firmament.events.FLevelChangeEvent;
import me.coopersully.Firmament.events.FJoinEvent;
import me.coopersully.Firmament.events.FQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FirmamentPlugin extends JavaPlugin {

    public static Firmament worldBorder;

    public static String settingsNamespace = "world";
    public static int settingsMultiplier = 0;
    public static boolean settingsAnnouncements = true;

    public static boolean sacrificeEnabled = true;
    public static int sacrificePercentage = 10;

    public static int permanentBlocks = 0;

    public void onEnable() {

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
    }

    public void onDisable() { }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        switch (label) {
            case "firmament", "firm", "f", "world-border", "worldborder", "wb", "border" -> {
                FirmamentCommands.firmament(sender, label, args);
                return true;
            }
            case "sacrifice", "sac", "s", "offer" -> {
                FirmamentCommands.sacrifice(sender, label, args);
                return true;
            }
            case "minimum", "min", "checkmin" -> {
                FirmamentCommands.findMin(sender);
                return true;
            }
            case "maximum", "max", "checkmax" -> {
                FirmamentCommands.findMax(sender);
                return true;
            }
        }

        return false;

    }

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("Firmament");
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
    }

}
