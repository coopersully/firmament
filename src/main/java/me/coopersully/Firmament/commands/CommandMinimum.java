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

public class CommandMinimum implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = CoreUtils.getPlayerFromSender(sender);
        if (player == null) return false;

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

        return false;

    }

}
