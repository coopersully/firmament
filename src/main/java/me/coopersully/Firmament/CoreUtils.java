package me.coopersully.Firmament;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CoreUtils {

    public static @Nullable Player getPlayerFromSender(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(org.bukkit.ChatColor.RED + "This command must be executed via a player.");
            return null;
        }
        return player;
    }

    public static @NotNull List<ItemStack> getItemsFromStrings(@NotNull List<String> materials) {
        List<ItemStack> items = new ArrayList<>();
        for (var material : materials) {
            var thisMaterial = Material.valueOf(material);
            var thisItem = new ItemStack(thisMaterial, 1);
            items.add(thisItem);
        }
        return items;
    }

    public static @NotNull String getColoredMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
