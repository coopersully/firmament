package me.coopersully.Firmament.events;

import me.coopersully.Firmament.Firmament;
import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FDeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FirmamentPlugin.getPlugin(), () -> handleEvent(event));
    }

    private void handleEvent(PlayerDeathEvent event) {
        FirmamentPlugin.worldBorder.refresh(false, false, event);
    }

}
