package me.coopersully.Firmament;

import me.coopersully.Firmament.commands.*;
import me.coopersully.Firmament.config.ConfigLang;
import me.coopersully.Firmament.config.ConfigMain;
import me.coopersully.Firmament.events.FDeathEvent;
import me.coopersully.Firmament.events.FJoinEvent;
import me.coopersully.Firmament.events.FLevelChangeEvent;
import me.coopersully.Firmament.events.FQuitEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class FirmamentPlugin extends JavaPlugin {

    private static FirmamentPlugin instance;
    public static Firmament worldBorder;


    public void onEnable() {

        // Initialize instance
        instance = this;

        // Enable all listeners
        getServer().getPluginManager().registerEvents(new FLevelChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new FJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new FQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new FDeathEvent(), this);

        // Generate configuration files
        saveDefaultConfig();
        ConfigMain.reload();

        ConfigLang.create();
        ConfigLang.reload();

        // Initialize borders
        if (getServer().getWorld(ConfigMain.getNamespace()) == null) {
            System.out.println(ChatColor.RED + "No worlds by the name of \"" + ConfigMain.getNamespace() + "\" were found; please check if your config.yml is correctly configured.");
            System.out.println(ChatColor.RED + "Disabling the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        // Initialize "worldBorder" with name of world
        worldBorder = new Firmament(ConfigMain.getNamespace());
        worldBorder.refresh(true, true);

        // Register all commands
        getServer().getPluginCommand("firmament").setExecutor(new CommandFirmament());
        getServer().getPluginCommand("sacrifice").setExecutor(new CommandSacrifice());
        getServer().getPluginCommand("minimum").setExecutor(new CommandMinimum());
        getServer().getPluginCommand("maximum").setExecutor(new CommandMaximum());
        getServer().getPluginCommand("startingitems").setExecutor(new CommandStartingItems());
    }

    public void onDisable() { }

    public static FirmamentPlugin getPlugin() {
        return instance;
    }


}
