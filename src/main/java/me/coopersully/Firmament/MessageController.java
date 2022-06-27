package me.coopersully.Firmament;

import me.coopersully.Firmament.config.ConfigLang;
import me.coopersully.Firmament.config.ConfigMain;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageController {

    public static void announce(@NotNull List<String> messages) {
        if (!ConfigMain.canAnnounce()) return;
        for (var message : messages) announce(message);
    }

    public static void announce(String message) {
        if (!ConfigMain.canAnnounce()) return;
        Bukkit.broadcast(Component.text(ConfigLang.getPrefix() + message));
    }

    public static void tell(@NotNull Player player, @NotNull List<String> messages) {
        for (var message : messages) tell(player, message);
    }

    public static void tell(@NotNull Player player, String message) {
        player.sendMessage(Component.text(ConfigLang.getPrefix() + message));
    }

}
