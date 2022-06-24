package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class FDeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        FirmamentPlugin.worldBorder.refresh(false, false, event);
    }

}
