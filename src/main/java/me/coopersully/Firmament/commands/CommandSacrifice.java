package me.coopersully.Firmament.commands;

import me.coopersully.Firmament.CoreUtils;
import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSacrifice implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = CoreUtils.getPlayerFromSender(sender);
        if (player == null) return false;

        if (!FirmamentPlugin.sacrificeEnabled) {
            sender.sendMessage(ChatColor.RED + "Sacrifices are not being accepted at this time.");
            return true;
        }

        if (args.length < 1) return false;

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) { return false; }

        if (amount < (FirmamentPlugin.worldBorder.getSize() / FirmamentPlugin.sacrificePercentage)) {
            sender.sendMessage(ChatColor.RED + "Your sacrifice must be " + (FirmamentPlugin.worldBorder.getSize() / FirmamentPlugin.sacrificePercentage) + " levels or greater.");
            return true;
        }

        if (amount > player.getLevel()) {
            sender.sendMessage(ChatColor.RED + "You do not have enough levels to sacrifice that amount.");
            return true;
        }

        player.setLevel(player.getLevel() - amount);
        FirmamentPlugin.getInstance().getConfig().set("permanent-blocks", FirmamentPlugin.permanentBlocks + amount);
        FirmamentPlugin.getInstance().saveDefaultConfig();
        FirmamentPlugin.reloadDefaultConfig();

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bx&7] &b" + sender.getName() + " sacrificed " + amount + " levels to the firmament."));
        sender.sendMessage(ChatColor.BLUE + "The firmament now has " + FirmamentPlugin.permanentBlocks + " permanent blocks.");

        return false;

    }

}
