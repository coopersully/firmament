package me.coopersully.Firmament;

import me.coopersully.Firmament.events.FLevelChangeEvent;
import me.coopersully.Firmament.events.FLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class FirmamentPlugin extends JavaPlugin {

    public static BorderController worldBorder;
    public static int permanentBlocks = 0;
    public static int multiplier = 0;

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("Firmament");
    }

    public void onEnable() {

        // Check for existing worlds
        List<World> worldList = getServer().getWorlds();
        if (worldList.size() == 0) {
            getServer().getPluginManager().disablePlugin(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No worlds found on this server; disabling Firmament.");
        }

        // Enable all listeners
        getServer().getPluginManager().registerEvents(new FLevelChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new FLoginEvent(), this);

        // Generate config
        saveDefaultConfig();
        permanentBlocks = getConfig().getInt("permanent-blocks");
        multiplier = getConfig().getInt("multiplier");

        // Initialize border
        worldBorder = new BorderController(getServer().getWorlds().get(0));
        worldBorder.refresh(true);
    }

    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        switch (label) {
            case "help", "ehelp", "hhelp", "faq", "?" -> {
                FirmamentCommands.help(sender);
                return true;
            }
            case "refresh", "forcecheck", "force-check", "fc" -> {
                FirmamentCommands.refresh(sender);
                return true;
            }
            case "sacrifice", "sac", "s" -> {
                FirmamentCommands.sacrifice(sender, args);
                return true;
            }
            case "info", "check", "size", "query" -> {
                FirmamentCommands.getSize(sender);
                return true;
            }
            case "min", "checkmin" -> {
                FirmamentCommands.whoIsLowest(sender);
                return true;
            }
            case "max", "checkmax" -> {
                FirmamentCommands.whoIsHighest(sender);
                return true;
            }
        }

        return false;

    }

}
