package me.coopersully.Firmament.events;

import me.coopersully.Firmament.FirmamentPlugin;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class FLevelChangeEvent implements Listener {

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {

        FirmamentPlugin.worldBorder.refresh(false);

        if (!FirmamentPlugin.getInstance().getConfig().getBoolean("settings.announcements")) return;

        Player player = event.getPlayer();
        if (FirmamentPlugin.worldBorder.getSize() > FirmamentPlugin.worldBorder.getOldSize()) {

            TextComponent user_agreement = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7[&a+&7] &aThe firmament has expanded to a diameter of " + FirmamentPlugin.worldBorder.getSize() + "."));
            user_agreement.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&b" + player.getName() + "&7 went from level &b" + event.getOldLevel() + "&7 to level &b" + event.getNewLevel()))));
            Bukkit.spigot().broadcast(user_agreement);

        } else if (FirmamentPlugin.worldBorder.getSize() < FirmamentPlugin.worldBorder.getOldSize()) {

            TextComponent user_agreement = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&7[&c-&7] &cThe firmament has shrunk to a diameter of " + FirmamentPlugin.worldBorder.getSize() + "."));
            user_agreement.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&b" + player.getName() + "&7 went from level &b" + event.getOldLevel() + "&7 to level &b" + event.getNewLevel()))));
            Bukkit.spigot().broadcast(user_agreement);

        }

    }

}
