package me.coopersully.Firmament.commands;

import me.coopersully.Firmament.CoreUtils;
import me.coopersully.Firmament.FirmamentPlugin;
import me.coopersully.Firmament.MessageController;
import me.coopersully.Firmament.config.ConfigLang;
import me.coopersully.Firmament.config.ConfigMain;
import org.bukkit.Bukkit;
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

        if (!ConfigMain.isSacrificeEnabled()) {
            MessageController.tell(player, ConfigLang.getSacrificeFailedDisabled());
            return true;
        }

        if (args.length < 1) return false;

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) { return false; }

        if (amount < (FirmamentPlugin.worldBorder.getSize() / ConfigMain.getSacrificePercentage())) {
            MessageController.tell(player, ConfigLang.getSacrificeFailedMinimum().replace("%minimum%", "" + (FirmamentPlugin.worldBorder.getSize() / ConfigMain.getSacrificePercentage())));
            return true;
        }

        if (amount > player.getLevel()) {
            MessageController.tell(player, ConfigLang.getSacrificeFailedExperience());
            return true;
        }

        // Remove sacrificed levels from player
        player.setLevel(player.getLevel() - amount);

        // Update permanent blocks in configuration
        FirmamentPlugin.getPlugin().getConfig().set("permanent-blocks", ConfigMain.getPermanentBlocks() + amount);
        FirmamentPlugin.getPlugin().saveConfig();

        // Load new configuration into memory for server
        ConfigMain.reload();

        MessageController.announce(
                ConfigLang.getSacrificeSuccessBroadcast()
                        .replace("%player%", player.getName())
                        .replace("%amount%", "" + amount)
        );

        return true;

    }

}
