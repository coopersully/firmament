package me.coopersully.Firmament;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FirmamentCommands {

    public static void firmament(CommandSender sender, String label, String[] args) {

        if (args.length < 1) {
            help(sender, label);
            return;
        }

        switch (args[0]) {
            case "help", "commands", "cmds", "faq", "?" -> help(sender, label);
            case "reload" -> reload(sender);
            case "refresh" -> refresh(sender);
            case "width", "info", "size" -> width(sender);
            default -> sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <help/reload/refresh/width>");
        }
    }

    public static void help(CommandSender sender, String label) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lThe Firmament &r| &7Author: &bCursedImpulse"));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7As players gain experience, the world border (\"firmament\") will grow, but as players lose experience via enchantment or death, the firmament will shrink. &eThe current firmament will grow 2 Blocks : 1 Level each player gains."));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &e/" + label + " width &7displays the firmament's statistics."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &e/" + label +  " reload &7reloads the plugin's configuration."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &e/" + label + " refresh &7reloads the firmament's borders."));
    }

    public static void refresh(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "The firmament's width was successfully refreshed.");
        FirmamentPlugin.worldBorder.refresh(false, false);
    }

    public static void reload(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "Firmament successfully reloaded.");
        FirmamentPlugin.reloadDefaultConfig();
    }

    public static void sacrifice(CommandSender sender, String label, String[] args) {

        if (!FirmamentPlugin.sacrificeEnabled) {
            sender.sendMessage(ChatColor.RED + "Sacrifices are not being accepted at this time.");
            return;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run via the console.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <amount>");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <amount>");
            return;
        }

        if (amount < (FirmamentPlugin.worldBorder.getSize() / FirmamentPlugin.sacrificePercentage)) {
            sender.sendMessage(ChatColor.RED + "Your sacrifice must be " + (FirmamentPlugin.worldBorder.getSize() / FirmamentPlugin.sacrificePercentage) + " levels or greater.");
            return;
        }

        if (amount > player.getLevel()) {
            sender.sendMessage(ChatColor.RED + "You do not have enough levels to sacrifice that amount.");
            return;
        }

        player.setLevel(player.getLevel() - amount);
        FirmamentPlugin.getInstance().getConfig().set("permanent-blocks", FirmamentPlugin.permanentBlocks + amount);
        FirmamentPlugin.getInstance().saveDefaultConfig();
        FirmamentPlugin.reloadDefaultConfig();

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bx&7] &b" + sender.getName() + " sacrificed " + amount + " levels to the firmament."));
        sender.sendMessage(ChatColor.BLUE + "The firmament now has " + FirmamentPlugin.permanentBlocks + " permanent blocks.");
    }

    public static void width(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7The firmament is currently &b" + FirmamentPlugin.worldBorder.getSize() + " &7blocks wide."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7* &b" + (FirmamentPlugin.worldBorder.getSize() - FirmamentPlugin.permanentBlocks) + " &7blocks are due to player &bexperience levels&7."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7* &b" + FirmamentPlugin.permanentBlocks + " &7blocks are due to &bpermanent sacrifices&7."));
    }

    public static void findMin(CommandSender sender) {
        Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
        Player minPlayer = null;
        for (Player currPlayer : playerList) {
            if (minPlayer == null) {
                minPlayer = currPlayer;
            }
            if (currPlayer.getTotalExperience() < minPlayer.getTotalExperience()) {
                if (currPlayer.isOnline() && !currPlayer.isDead() && currPlayer.isValid()) minPlayer = currPlayer;
            }
        }

        assert minPlayer != null;
        sender.sendMessage(ChatColor.AQUA + minPlayer.getName() + " has the least amount of experience.");
    }

    public static void findMax(CommandSender sender) {
        Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
        Player maxPlayer = null;
        for (Player currPlayer : playerList) {
            if (maxPlayer == null) {
                maxPlayer = currPlayer;
            }
            if (currPlayer.getTotalExperience() > maxPlayer.getTotalExperience()) {
                if (currPlayer.isOnline() && !currPlayer.isDead() && currPlayer.isValid()) maxPlayer = currPlayer;
            }
        }

        assert maxPlayer != null;
        sender.sendMessage(ChatColor.AQUA + maxPlayer.getName() + " has the most amount of experience.");
    }

}
