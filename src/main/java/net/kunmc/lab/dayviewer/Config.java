package net.kunmc.lab.dayviewer;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private int firstDay = 1;
    private int lastDay = 100;
    private double dayMin = 6;

    Config() {
        loadConfig(false);
    }

    public boolean loadConfig() {
        return loadConfig(true);
    }

    public boolean loadConfig(boolean isReload) {
        DayViewer plugin = DayViewer.plugin;

        plugin.saveDefaultConfig();

        if (isReload) {
            plugin.reloadConfig();
        }

        FileConfiguration config = plugin.getConfig();

        try {
            setDayMin(config.getDouble("day_min"));
            setFirstDay(config.getInt("first_day"));
            setLastDay(config.getInt("last_day"));
        } catch (Exception ignore) {
            return false;
        }

        return true;
    }

    public int getFirstDay() {
        return firstDay;
    }

    public boolean setFirstDay(int firstDay) {
        if (firstDay < 0) return false;

        this.firstDay = firstDay;
        return true;
    }

    public int getLastDay() {
        return lastDay;
    }

    public boolean setLastDay(int lastDay) {
        if (firstDay < 0) return false;

        this.lastDay = lastDay;
        return true;
    }

    public double getDayMin() {
        return dayMin;
    }

    public boolean setDayMin(double dayMin) {
        if (dayMin < 0.1) return false;

        this.dayMin = dayMin;
        return true;
    }
}