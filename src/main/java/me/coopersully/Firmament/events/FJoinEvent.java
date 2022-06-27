package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import me.coopersully.Firmament.commands.CommandStartingItems;
import me.coopersully.Firmament.config.ConfigMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class FJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FirmamentPlugin.getPlugin(), () -> {

            // If the player spawns outside the firmament
            Player player = event.getPlayer();
            if (isOutsideBorder(player)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (15 * 20), 4));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You are &etemporarily invulnerable &7for &e15 seconds&7."));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Your grace period will end soon; ensure that you are within the firmament's borders."));
            }

            FirmamentPlugin.worldBorder.refresh(false, false, event);

            // If it's the player's first time playing, give them required items and possible items.
            if (!player.hasPlayedBefore()) CommandStartingItems.giveAllItems(player);

        }, 1);
    }

    public boolean isOutsideBorder(@NotNull Player player) {

        double radius = FirmamentPlugin.worldBorder.getRadius();

        Location location = player.getLocation();
        Location center = FirmamentPlugin.worldBorder.getCenter();
        center.setWorld(player.getWorld());

        return center.distanceSquared(location) >= (radius * radius);

    }


}
