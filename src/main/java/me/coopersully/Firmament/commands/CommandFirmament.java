package me.coopersully.Firmament.commands;

import me.coopersully.Firmament.FirmamentPlugin;
import me.coopersully.Firmament.config.ConfigMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandFirmament implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 1) {
            help(sender, label);
            return true;
        }

        switch (args[0]) {
            case "help", "commands", "cmds", "faq", "?":
                help(sender, label);
                return true;
            case "reload":
                reload(sender);
                return true;
            case "refresh":
                refresh(sender);
                return true;
            case "width", "info", "size":
                width(sender);
                return true;
            default:
                return false;
        }

    }

    private static void help(@NotNull CommandSender sender, String label) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lThe Firmament v" + FirmamentPlugin.getPlugin().getDescription().getVersion()));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7As players gain experience, the world border (\"firmament\") will grow, but as players lose experience via enchantment or death, the firmament will shrink. &eThe current firmament will grow 2 Blocks : 1 Level each player gains."));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &e/" + label + " width &7displays the firmament's statistics."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &e/" + label +  " reload &7reloads the plugin's configuration."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &e/" + label + " refresh &7reloads the firmament's borders."));
    }

    private static void width(@NotNull CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The firmament is currently &b" + FirmamentPlugin.worldBorder.getSize() + " &7blocks wide."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &b" + (FirmamentPlugin.worldBorder.getSize() - ConfigMain.getPermanentBlocks()) + " &7blocks are due to player &bexperience levels&7."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7* &b" + ConfigMain.getPermanentBlocks() + " &7blocks are due to &bpermanent sacrifices&7."));
    }

    private static void refresh(@NotNull CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "The firmament's width was successfully refreshed.");
        FirmamentPlugin.worldBorder.refresh(false, false);
    }

    private static void reload(@NotNull CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Firmament successfully reloaded.");
        ConfigMain.reload();
    }

}
