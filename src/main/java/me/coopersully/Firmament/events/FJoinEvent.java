package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
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

        // If the player spawns outside the firmament
        Player player = event.getPlayer();
        if (isOutsideBorder(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (15 * 20), 4));
            player.sendMessage(ChatColor.BLUE + "You are temporarily invulnerable for 15 seconds.");
            player.sendMessage(ChatColor.BLUE + "Your grace period will end soon; ensure that you are within the firmament's borders.");
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FirmamentPlugin.getInstance(), () -> {

            FirmamentPlugin.worldBorder.refresh(false, false, event);

            /* If it's the player's first time playing,
            give them required items and possible items. */
            if (!player.hasPlayedBefore()) {

                // Give all guaranteed items
                givePlayerItems(player, FirmamentPlugin.guaranteedItems);

                // Give one of possible items
                Random random = new Random();
                var randomIndex = random.nextInt(FirmamentPlugin.possibleItems.size());
                player.getInventory().addItem(FirmamentPlugin.possibleItems.get(randomIndex));
            }

        }, 1);

    }

    public boolean isOutsideBorder(@NotNull Player player) {

        double radius = FirmamentPlugin.worldBorder.getRadius();

        Location location = player.getLocation();
        Location center = FirmamentPlugin.worldBorder.getCenter();
        center.setWorld(player.getWorld());

        return center.distanceSquared(location) >= (radius * radius);

    }

    private void givePlayerItems(@NotNull Player player, @NotNull List<ItemStack> items) {
        for (var item : items) {
            player.getInventory().addItem(item);
        }
    }

}
