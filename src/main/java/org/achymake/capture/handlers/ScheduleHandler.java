package org.achymake.capture.handlers;

import org.achymake.capture.Capture;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class ScheduleHandler {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private BukkitScheduler getBukkitScheduler() {
        return getInstance().getBukkitScheduler();
    }
    public BukkitTask runTimer(Runnable runnable, long tick, long period) {
        return getBukkitScheduler().runTaskTimer(getInstance(), runnable, tick, period);
    }
    public BukkitTask runLater(Runnable runnable, long tick) {
        return getBukkitScheduler().runTaskLater(getInstance(), runnable, tick);
    }
    public void runAsynchronously(Runnable runnable) {
        getBukkitScheduler().runTaskAsynchronously(getInstance(), runnable);
    }
    public boolean isQueued(int taskID) {
        return getBukkitScheduler().isQueued(taskID);
    }
    public void cancel(int taskID) {
        if (isQueued(taskID)) {
            getBukkitScheduler().cancelTask(taskID);
        }
    }
    public void disable() {
        getBukkitScheduler().cancelTasks(getInstance());
    }
}