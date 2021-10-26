package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class FQuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(FirmamentPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                FirmamentPlugin.worldBorder.refresh(false, false, event);
            }
        }, 1);

    }
}
