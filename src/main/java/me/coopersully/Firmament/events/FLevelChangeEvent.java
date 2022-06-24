package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class FLevelChangeEvent implements Listener {

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        FirmamentPlugin.worldBorder.refresh(false, false, event);
    }

}
