package me.coopersully.Firmament;

import me.coopersully.Firmament.events.FLevelChangeEvent;
import me.coopersully.Firmament.events.FLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FirmamentPlugin extends JavaPlugin {

    public static BorderController worldBorder;
    public static int permanentBlocks = 0;
    public static int multiplier = 0;

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("Firmament");
    }

    public void onEnable() {

        // Enable all listeners
        getServer().getPluginManager().registerEvents(new FLevelChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new FLoginEvent(), this);

        // Generate config
        saveDefaultConfig();
        permanentBlocks = getConfig().getInt("permanent-blocks");
        multiplier = getConfig().getInt("settings.multiplier");

        // Initialize border
        worldBorder = new BorderController(getServer().getWorlds().get(0));
        worldBorder.refresh(true);
    }

    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("firmament") || label.equalsIgnoreCase("firm") || label.equalsIgnoreCase("f")) {
            if (args.length < 1) return false;

            switch (args[0]) {
                case "help", "ehelp", "hhelp", "faq", "?" -> {
                    FirmamentCommands.help(sender);
                    return true;
                }
                case "refresh", "forcecheck", "force-check", "fc" -> {
                    FirmamentCommands.refresh(sender);
                    return true;
                }
                case "reload", "rel", "r" -> {
                    FirmamentCommands.reload();
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
                default -> {
                    sender.sendMessage(ChatColor.RED + "Did you mean /" + label + " help?");
                    return true;
                }
            }

        }

        return false;

    }

}
