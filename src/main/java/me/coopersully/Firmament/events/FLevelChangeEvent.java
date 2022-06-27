package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import me.coopersully.Firmament.MessageController;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.jetbrains.annotations.NotNull;

public class FLevelChangeEvent implements Listener {
    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FirmamentPlugin.getPlugin(), () -> handleEvent(event));
    }

    private void handleEvent(@NotNull PlayerLevelChangeEvent event) {
        FirmamentPlugin.worldBorder.refresh(false, false, event);
    }
}
