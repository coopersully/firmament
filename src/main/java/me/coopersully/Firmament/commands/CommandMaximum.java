package me.coopersully.Firmament.commands;

import me.coopersully.Firmament.CoreUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class CommandMaximum implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = CoreUtils.getPlayerFromSender(sender);
        if (player == null) return false;

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

        return true;

    }

}
