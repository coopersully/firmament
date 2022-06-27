package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class FQuitEvent implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FirmamentPlugin.getPlugin(), () -> handleEvent(event));
    }

    private void handleEvent(PlayerQuitEvent event) {
        FirmamentPlugin.worldBorder.refresh(false, false, event);
    }
}
