package me.coopersully.Firmament;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import java.util.Collection;

public class BorderController {

    int size;
    int oldSize;
    WorldBorder reference;

    public BorderController(World world) {
        this.size = FirmamentPlugin.multiplier;
        this.oldSize = FirmamentPlugin.multiplier;

        reference = world.getWorldBorder();
        reference.setCenter(0.5, 0.5);
        reference.setDamageBuffer(0.0);
        reference.setSize(this.size);
    }

    public Location getCenter() {
        return reference.getCenter();
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int newSize) {

        this.oldSize = size;
        this.size = FirmamentPlugin.permanentBlocks + (newSize * 2);

        if (this.size < FirmamentPlugin.multiplier) this.size = FirmamentPlugin.multiplier;

        reference.setSize(this.size, Math.abs(this.size - oldSize));

    }

    public int getRadius() {
        return this.size / 2;
    }

    public int getOldSize() {
        return this.oldSize;
    }

    public void setSizeInstant(int newSize) {

        this.oldSize = size;
        this.size = FirmamentPlugin.permanentBlocks + (newSize * 2);

        if (this.size < FirmamentPlugin.multiplier) this.size = FirmamentPlugin.multiplier;

        reference.setSize(this.size, 0);

    }

    public void refresh(boolean isInstant) {
        // Get the total levels for all online players
        Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
        int total = 0;
        for (Player currPlayer : playerList) {
            total += currPlayer.getLevel();
        }

        // Set the size to the total
        if (isInstant) {
            setSizeInstant(total);
            return;
        }
        setSize(total);
    }

}
