package net.kunmc.lab.dayviewer;

import org.bukkit.scheduler.BukkitRunnable;

public class Task extends BukkitRunnable {
    private DayViewer plugin;
    private int today;
    private int lastDay;

    Task(int first, int last) {
        this.today = first;
        this.lastDay = last;
        this.plugin = DayViewer.plugin;

        String message = String.format("%d 日目", today);
        sendTitle(message);
    }

    public int getToday() {
        return today;
    }

    private void sendTitle(String message) {
        plugin.getServer().getOnlinePlayers()
                .stream().parallel()
                .forEach(v -> v.sendTitle(message, ""));
    }

    @Override
    public void run() {
        ++today;

        if (today <= lastDay) {
            String message = String.format("%d 日目", today);
            sendTitle(message);
        } else {
            String message = "ゲームクリア";
            sendTitle(message);
            this.cancel();
        }
    }
}