package me.coopersully.Firmament;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Firmament {

    int size;
    int oldSize;
    WorldBorder overworld = null;
    WorldBorder nether = null;
    WorldBorder the_end = null;

    public Firmament(String namespace) {

        // Overworld cannot be null because of previous checks from FirmamentPlugin
        overworld = FirmamentPlugin.getInstance().getServer().getWorld(namespace).getWorldBorder();

        // Check if nether exists
        try {
            nether = FirmamentPlugin.getInstance().getServer().getWorld(namespace + "_nether").getWorldBorder();
        } catch (NullPointerException e) {
            System.out.println(ChatColor.YELLOW + "WARNING: No nether dimension found, the firmament will not extend to it.");
        }

        // Check if the end exists
        try {
            the_end = FirmamentPlugin.getInstance().getServer().getWorld(namespace + "_the_end").getWorldBorder();
        } catch (NullPointerException e) {
            System.out.println(ChatColor.YELLOW + "WARNING: No end dimension found, the firmament will not extend to it.");
        }

        // Set default size records to the multiplier set in config.yml
        this.size = FirmamentPlugin.multiplier;
        this.oldSize = FirmamentPlugin.multiplier;

        // Set default(s) for center, damage, and size (defined above)
        overworld.setCenter(0.5, 0.5);
        overworld.setDamageBuffer(0.0);
        overworld.setSize(this.size);

        if (hasEnd()) {
            the_end.setCenter(0.5, 0.5);
            the_end.setDamageBuffer(0.0);
            the_end.setSize(this.size);
        }

        if (hasNether()) {
            nether.setCenter(0.5, 0.5);
            nether.setDamageBuffer(0.0);
            nether.setSize(this.size);
        }

    }

    public Location getCenter() {
        return overworld.getCenter();
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int newSize) {

        this.oldSize = size;
        this.size = FirmamentPlugin.permanentBlocks + (newSize * 2);

        if (this.size < FirmamentPlugin.multiplier) this.size = FirmamentPlugin.multiplier;

        overworld.setSize(this.size, Math.abs(this.size - oldSize));
        if (hasNether()) nether.setSize(this.size, Math.abs(this.size - oldSize));
        if (hasEnd()) the_end.setSize(this.size, Math.abs(this.size - oldSize));

    }

    public void setSizeInstant(int newSize) {

        this.oldSize = size;
        this.size = FirmamentPlugin.permanentBlocks + (newSize * 2);

        if (this.size < FirmamentPlugin.multiplier) this.size = FirmamentPlugin.multiplier;

        overworld.setSize(this.size, 0);
        if (hasNether()) nether.setSize(this.size, 0);
        if (hasEnd()) the_end.setSize(this.size, 0);

    }


    public int getRadius() {
        return this.size / 2;
    }

    public int getOldSize() {
        return this.oldSize;
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

    public boolean hasNether() {
        return nether != null;
    }

    public boolean hasEnd() {
        return the_end != null;
    }

}
