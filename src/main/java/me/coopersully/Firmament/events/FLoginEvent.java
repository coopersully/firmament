package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FLoginEvent implements Listener {

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        FirmamentPlugin.worldBorder.refresh(false);

        // If the player spawns outside the firmament
        if (isOutsideBorder(player)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (15 * 20), 4));
            player.sendMessage(ChatColor.BLUE + "You are temporarily invulnerable for 15 seconds.");
            player.sendMessage(ChatColor.BLUE + "Your grace period will end soon; ensure that you are within the firmament's borders.");
        }

    }

    public boolean isOutsideBorder(Player player) {
        double radius = FirmamentPlugin.worldBorder.getRadius();
        Location location = player.getLocation();
        Location center = FirmamentPlugin.worldBorder.getCenter();
        center.setWorld(player.getWorld());
        return center.distanceSquared(location) >= (radius * radius);
    }

}
