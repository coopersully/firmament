package me.coopersully.Firmament.commands;

import me.coopersully.Firmament.CoreUtils;
import me.coopersully.Firmament.FirmamentPlugin;
import me.coopersully.Firmament.config.ConfigMain;
import me.coopersully.Firmament.events.FJoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class CommandStartingItems implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = CoreUtils.getPlayerFromSender(sender);
        if (player == null) return false;

        // Give all guaranteed items
        givePlayerItems(player, ConfigMain.getGuaranteedItems());

        // Give one of possible items
        Random random = new Random();
        var randomIndex = random.nextInt(ConfigMain.getPossibleItems().size());
        player.getInventory().addItem(ConfigMain.getPossibleItems().get(randomIndex));

        return true;

    }

    public static void giveAllItems(Player player) {
        // Give all guaranteed items
        givePlayerItems(player, ConfigMain.getGuaranteedItems());

        // Give one of possible items
        Random random = new Random();
        var randomIndex = random.nextInt(ConfigMain.getPossibleItems().size());
        player.getInventory().addItem(ConfigMain.getPossibleItems().get(randomIndex));
    }

    private static void givePlayerItems(@NotNull Player player, @NotNull List<ItemStack> items) {
        for (var item : items) {
            player.getInventory().addItem(item);
        }
    }

}
