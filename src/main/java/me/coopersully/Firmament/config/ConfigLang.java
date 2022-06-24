package me.coopersully.Firmament.config;

import me.coopersully.Firmament.CoreUtils;
import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigLang implements PluginConfig {

    private static FileConfiguration file;

    private static String prefix;
    private static String sacrificeFailedPermission;
    private static String sacrificeFailedExperience;
    private static String sacrificeFailedDisabled;
    private static String sacrificeFailedMinimum;
    private static String sacrificeSuccessBroadcast;

    public static void reload() {
        prefix = CoreUtils.getColoredMessage(file.getString("prefix"));

        var sacrifice = file.getConfigurationSection("sacrifice");

        assert sacrifice != null;
        var sacrificeFailure = sacrifice.getConfigurationSection("failure");

        assert sacrificeFailure != null;
        sacrificeFailedPermission = CoreUtils.getColoredMessage(sacrificeFailure.getString("permission"));
        sacrificeFailedExperience = CoreUtils.getColoredMessage(sacrificeFailure.getString("experience"));
        sacrificeFailedDisabled = CoreUtils.getColoredMessage(sacrificeFailure.getString("disabled"));
        sacrificeFailedMinimum = CoreUtils.getColoredMessage(sacrificeFailure.getString("minimum"));

        var sacrificeSuccess = sacrifice.getConfigurationSection("success");
        sacrificeSuccessBroadcast = CoreUtils.getColoredMessage(sacrificeSuccess.getString("broadcast"));

    }

    public static FileConfiguration get() {
        return file;
    }

    public static void create() {
        File lang = new File(FirmamentPlugin.getPlugin().getDataFolder(), "lang/en_US.yml");
        if (!lang.exists()) {
            lang.getParentFile().mkdirs();
            FirmamentPlugin.getPlugin().saveResource("lang/en_US.yml", false);
        }

        file = new YamlConfiguration();
        try {
            file.load(lang);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getPrefix() {
        return prefix;
    }

    public static String getSacrificeFailedDisabled() {
        return sacrificeFailedDisabled;
    }

    public static String getSacrificeFailedExperience() {
        return sacrificeFailedExperience;
    }

    public static String getSacrificeFailedPermission() {
        return sacrificeFailedPermission;
    }

    public static String getSacrificeFailedMinimum() {
        return sacrificeFailedMinimum;
    }

    public static String getSacrificeSuccessBroadcast() {
        return sacrificeSuccessBroadcast;
    }
}
