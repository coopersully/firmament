package me.coopersully.Firmament.config;

import me.coopersully.Firmament.CoreUtils;
import me.coopersully.Firmament.FirmamentPlugin;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigMain implements PluginConfig {

    private static String namespace = "world";
    private static int growthRate = 0;
    private static boolean canAnnounce = true;
    private static boolean sacrificeEnabled = true;
    private static int sacrificePercentage = 10;
    private static int permanentBlocks = 0;
    private static List<ItemStack> guaranteedItems;
    private static List<ItemStack> possibleItems;

    public static void reload() {
        FirmamentPlugin.getPlugin().reloadConfig();
        FirmamentPlugin.getPlugin().saveDefaultConfig();

        // Settings section
        namespace = FirmamentPlugin.getPlugin().getConfig().getString("settings.namespace");
        growthRate = FirmamentPlugin.getPlugin().getConfig().getInt("settings.multiplier");
        canAnnounce = FirmamentPlugin.getPlugin().getConfig().getBoolean("settings.announcements");

        // Sacrifice section
        sacrificeEnabled = FirmamentPlugin.getPlugin().getConfig().getBoolean("sacrifice.enabled");
        sacrificePercentage = FirmamentPlugin.getPlugin().getConfig().getInt("sacrifice.percentage");

        // Storage section
        permanentBlocks = FirmamentPlugin.getPlugin().getConfig().getInt("permanent-blocks");

        // Join items section
        var startingItems = FirmamentPlugin.getPlugin().getConfig().getConfigurationSection("starting-items");
        guaranteedItems = CoreUtils.getItemsFromStrings(startingItems.getStringList("guaranteed"));
        possibleItems = CoreUtils.getItemsFromStrings(startingItems.getStringList("possibilities"));
    }

    public static String getNamespace() {
        return namespace;
    }

    public static boolean isSacrificeEnabled() {
        return sacrificeEnabled;
    }

    public static boolean canAnnounce() {
        return canAnnounce;
    }

    public static int getPermanentBlocks() {
        return permanentBlocks;
    }

    public static int getSacrificePercentage() {
        return sacrificePercentage;
    }

    public static int getGrowthRate() {
        return growthRate;
    }

    public static List<ItemStack> getGuaranteedItems() {
        return guaranteedItems;
    }

    public static List<ItemStack> getPossibleItems() {
        return possibleItems;
    }
}
