package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import me.coopersully.Firmament.MessageController;
import me.coopersully.Firmament.commands.CommandStartingItems;
import me.coopersully.Firmament.config.ConfigLang;
import me.coopersully.Firmament.config.ConfigMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class FJoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FirmamentPlugin.getPlugin(), () -> handleEvent(event));
    }

    public boolean isOutsideBorder(@NotNull Player player) {

        double radius = FirmamentPlugin.worldBorder.getRadius();

        Location location = player.getLocation();
        Location center = FirmamentPlugin.worldBorder.getCenter();
        center.setWorld(player.getWorld());

        return center.distanceSquared(location) >= (radius * radius);

    }

    private void handleEvent(@NotNull PlayerJoinEvent event) {
        // If the player spawns outside the firmament
        Player player = event.getPlayer();
        if (isOutsideBorder(player)) {
            var seconds = 15;
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (seconds * 20), 4));
            MessageController.tell(player, ConfigLang.getJoinProtection().replace("%time%", "" + seconds));
        }

        // If it's the player's first time playing, give them required items and possible items.
        if (!player.hasPlayedBefore()) CommandStartingItems.giveAllItems(player);

        // Refresh worldborder
        FirmamentPlugin.worldBorder.refresh(false, false, event);
    }

}
