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
    }

    public int getToday() {
        return today - 1;
    }

    private void sendTitle(String message) {
        plugin.getServer().broadcastMessage(message);
    }

    @Override
    public void run() {
        if (today <= lastDay) {
            String message = String.format(" %d 日目です", today);
            sendTitle(message);
            ++today;
        } else {
            String message = "ゲームクリア";
            sendTitle(message);
            DayViewer.plugin.stop();
        }
    }
}