package net.kunmc.lab.dayviewer;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class DayViewer extends JavaPlugin {
    public static DayViewer plugin;
    public Config config;

    private BukkitTask bukkitTask = null;
    private Task task = null;
    private boolean running = false;
    private int today = 1;

    @Override
    public void onEnable() {
        plugin = this;
        config = new Config();
        this.getCommand("day-viewer").setExecutor(new CommandManager());

    }

    public void start() {
        start(config.getFirstDay(), config.getLastDay());
    }

    public void resume() {
        start(today, config.getLastDay());
    }

    public void start(int firstDay, int lastDay) {
        if (running) return;
        running = true;

        double dayTicks = config.getDayMin() * 60 * 20;
        task = new Task(firstDay, lastDay);
        bukkitTask = task.runTaskTimer(this, 0, (long) dayTicks);
    }

    public void stop() {
        running = false;

        if (bukkitTask == null) return;
        if (!bukkitTask.isCancelled()) {
            bukkitTask.cancel();
        }

        today = task.getToday();
        task = null;
        bukkitTask = null;
    }
}
