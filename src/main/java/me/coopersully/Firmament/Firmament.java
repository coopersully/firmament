package me.coopersully.Firmament;

import me.coopersully.Firmament.config.ConfigLang;
import me.coopersully.Firmament.config.ConfigMain;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

public class Firmament {

    private int size;
    private int oldSize;
    private WorldBorder overworld;
    private WorldBorder nether = null;
    private WorldBorder the_end = null;

    public Firmament(String namespace) {

        // Overworld cannot be null because of previous checks from FirmamentPlugin
        overworld = FirmamentPlugin.getPlugin().getServer().getWorld(namespace).getWorldBorder();

        // Check if nether exists
        try {
            nether = FirmamentPlugin.getPlugin().getServer().getWorld(namespace + "_nether").getWorldBorder();
        } catch (NullPointerException e) {
            System.out.println(ChatColor.YELLOW + "WARNING: No nether dimension found, the firmament will not extend to it.");
        }

        // Check if the end exists
        try {
            the_end = FirmamentPlugin.getPlugin().getServer().getWorld(namespace + "_the_end").getWorldBorder();
        } catch (NullPointerException e) {
            System.out.println(ChatColor.YELLOW + "WARNING: No end dimension found, the firmament will not extend to it.");
        }

        // Set default size records to the multiplier set in config.yml
        this.size = ConfigMain.getGrowthRate();
        this.oldSize = ConfigMain.getGrowthRate();

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

    public void setSize(int newSize, boolean isInstant) {

        // Adjust size parameters for newSize (including sacrifices)
        this.oldSize = size;
        this.size = ConfigMain.getPermanentBlocks() + (newSize * 2);

        // Adjust size to meet minimum if necessary
        if (this.size < ConfigMain.getGrowthRate()) this.size = ConfigMain.getGrowthRate();

        // Set the time to 0 if it is an instant-refresh
        int time = 0;
        if (!isInstant) {
            // Distance between old & new border
            double dist = Math.abs(this.size - overworld.getSize());
            // Divided by the speed multiplier
            double realTime = (float) (dist / ConfigMain.getSpeed());
            time = (int) realTime;
        }

        // Set actual size of WorldBorder(s)
        overworld.setSize(this.size, time);
        if (hasNether()) nether.setSize(this.size, time);
        if (hasEnd()) the_end.setSize(this.size, time);

    }

    public int getRadius() {
        return this.size / 2;
    }

    public int getOldSize() {
        return this.oldSize;
    }

    public void refresh(boolean isInstant, boolean isSilent) {
        refresh(isInstant, isSilent, new PlayerCommandSendEvent(null, null));
    }

    public void refresh(boolean isInstant, boolean isSilent, Event event) {
        // Get the total levels for all online players
        Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();
        int total = 0;
        for (Player currPlayer : playerList) {
            // If the player is online, alive, and valid, add their levels to the total
            if (currPlayer.isOnline() && !currPlayer.isDead() && currPlayer.isValid()) {
                total += currPlayer.getLevel();
            }
        }

        // Set the size to the total
        setSize(total, isInstant);

        // Check if fluctuation should be announced
        if (isSilent) return;
        if (FirmamentPlugin.worldBorder.getSize() == FirmamentPlugin.worldBorder.getOldSize()) return;
        if (ConfigMain.canAnnounce()) announce(event);
    }

    public void announce(Event event) {

        /* All variables related to the fluctuation's HoverEvent
        showing the statistics of the border & event info. */
        String labelSource = "&8&lSource: &r", labelWidth = "&8&lWidth: &r", labelVolume = "&8&lVolume: &r";
        String width = labelWidth + "&b" + FirmamentPlugin.worldBorder.getOldSize() + " &7-> &b" + FirmamentPlugin.worldBorder.getSize() + "&7.\n";
        String volume = labelVolume + "&b" + prettifyNumber((int)(Math.pow(FirmamentPlugin.worldBorder.getSize(), 2) * 384)) + " &7blocks (assuming 384 y-range).";

        // Determine whether the fluctuation was an increase or decrease.
        String operation = "fluctuated";
        if (FirmamentPlugin.worldBorder.getSize() > FirmamentPlugin.worldBorder.getOldSize()) {
            operation = ConfigLang.getGeneralOperationIncrease();
            width = labelWidth + "&a" + FirmamentPlugin.worldBorder.getOldSize() + " &7->&a " + FirmamentPlugin.worldBorder.getSize() + "&7.\n";
        } else if (FirmamentPlugin.worldBorder.getSize() < FirmamentPlugin.worldBorder.getOldSize()) {
            operation = ConfigLang.getGeneralOperationDecrease();
            width = labelWidth + "&c" + FirmamentPlugin.worldBorder.getOldSize() + " &7->&c " + FirmamentPlugin.worldBorder.getSize() + "&7.\n";
        }

        /* Determines the reason for the border's fluctuation
        and adds info for the corresponding events. */
        String reason;
        if (event instanceof PlayerJoinEvent joinEvent) {
            reason = labelSource + "&b" + joinEvent.getPlayer().getName() + " &7joined the game.\n";
        } else if (event instanceof PlayerQuitEvent quitEvent) {
            reason = labelSource + "&b" + quitEvent.getPlayer().getName() + " &7left the game.\n";
        }
        else if (event instanceof PlayerDeathEvent deathEvent) {
            reason = labelSource + "&b" + PlainTextComponentSerializer.plainText().serialize(deathEvent.deathMessage()) + "&7.\n";
        }
        else if (event instanceof PlayerLevelChangeEvent levelChangeEvent) {
            reason = labelSource + "&b" + levelChangeEvent.getPlayer().getName() + " &7went from level &b" + levelChangeEvent.getOldLevel() + " &7to &b" + levelChangeEvent.getNewLevel() + "&7.\n";
        } else {
            reason = labelSource + "&cThe fluctuation's source is unknown.\n";
        }

        // Push completed announcement
        TextComponent announcement = Component.text(
                ConfigLang.getPrefix() + ConfigLang.getGeneralBroadcast().replace("%operation%", operation).replace("%size%", "" + size)
        ).hoverEvent(
                Component.text(ChatColor.translateAlternateColorCodes('&', reason + width + volume))
        );
        Bukkit.broadcast(announcement);

    }

    public String prettifyNumber(int number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    public boolean hasNether() {
        return nether != null;
    }

    public boolean hasEnd() {
        return the_end != null;
    }

}
