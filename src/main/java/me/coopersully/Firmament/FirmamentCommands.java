package me.coopersully.Firmament;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

import java.util.Collection;

public class FirmamentCommands {

    public static void help(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&lThe Firmament &r| &7Author: &bCursedImpulse"));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7As players gain experience, the world border (\"firmament\") will grow, but as players lose experience via enchantment or death, the firmament will shrink. &eThe current firmament will grow 2 Blocks : 1 Level each player gains."));
        sender.sendMessage("");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "- &e/f help &7displays the current chat menu."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "- &e/f info &7displays information about the current firmament."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "- &e/f sacrifice <amount> &7permanently sacrifices levels for permanent space in the firmament, purchased at a rate of &71 Level : 1 Block."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "- &e/f refresh &7forcibly refreshes the firmament."));
    }

    public static void refresh(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Firmament successfully refreshed.");
        FirmamentPlugin.worldBorder.refresh(false);
    }

    public static void reload() {
        FirmamentPlugin.getInstance().reloadConfig();
        FirmamentPlugin.getInstance().saveDefaultConfig();

        FirmamentPlugin.permanentBlocks = FirmamentPlugin.getInstance().getConfig().getInt("permanent-blocks");
        FirmamentPlugin.multiplier = FirmamentPlugin.getInstance().getConfig().getInt("settings.multiplier");
    }

    public static void sacrifice(CommandSender sender, String[] args) {

        if (!FirmamentPlugin.getInstance().getConfig().getBoolean("sacrifice.enabled")) {
            sender.sendMessage(ChatColor.RED + "Sacrifices are not being accepted at this time.");
            return;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be run via the console.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /f sacrifice <amount>");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Usage: /f sacrifice <amount>");
            return;
        }

        if (amount < (FirmamentPlugin.worldBorder.getSize() / 10)) {
            sender.sendMessage(ChatColor.RED + "Your sacrifice must be " + (FirmamentPlugin.worldBorder.getSize() / 10) + " levels or greater.");
            return;
        }

        if (amount > player.getLevel()) {
            sender.sendMessage(ChatColor.RED + "You do not have enough levels to sacrifice that amount.");
            return;
        }

        player.setLevel(player.getLevel() - amount);
        FirmamentPlugin.getInstance().getConfig().set("permanent-blocks", FirmamentPlugin.permanentBlocks + amount);
        FirmamentPlugin.getInstance().saveConfig();
        reload();

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bx&7] &b" + sender.getName() + " sacrificed " + amount + " levels to the firmament."));
        sender.sendMessage(ChatColor.BLUE + "The firmament now has " + FirmamentPlugin.permanentBlocks + " permanent blocks.");
    }

    public static void getSize(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "The firmament is currently " + FirmamentPlugin.worldBorder.getSize() + " blocks wide.");
        sender.sendMessage(ChatColor.GRAY + "- " + FirmamentPlugin.permanentBlocks + " of those blocks are permanent sacrifices.");
    }

    public static void findMin(CommandSender sender) {
        Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
        int totalExpLevels = 0;
        Player minPlayer = null;
        for (Player currPlayer : playerList) {
            if (minPlayer == null) {
                minPlayer = currPlayer;
            }
            if (currPlayer.getTotalExperience() < minPlayer.getTotalExperience()) {
                minPlayer = currPlayer;
            }
        }

        sender.sendMessage(ChatColor.AQUA + minPlayer.getName() + " has the least amount of experience.");
    }

    public static void findMax(CommandSender sender) {
        Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
        int totalExpLevels = 0;
        Player maxPlayer = null;
        for (Player currPlayer : playerList) {
            if (maxPlayer == null) {
                maxPlayer = currPlayer;
            }
            if (currPlayer.getTotalExperience() > maxPlayer.getTotalExperience()) {
                maxPlayer = currPlayer;
            }
        }

        sender.sendMessage(ChatColor.AQUA + maxPlayer.getName() + " has the most amount of experience.");
    }

}
