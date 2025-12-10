package org.achymake.capture.handlers;

import org.achymake.capture.Capture;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class ScheduleHandler {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private BukkitScheduler getScheduler() {
        return getInstance().getBukkitScheduler();
    }
    public BukkitTask runTimer(Runnable runnable, long tick, long period) {
        return getScheduler().runTaskTimer(getInstance(), runnable, tick, period);
    }
    public BukkitTask runLater(Runnable runnable, long tick) {
        return getScheduler().runTaskLater(getInstance(), runnable, tick);
    }
    public void runAsynchronously(Runnable runnable) {
        getScheduler().runTaskAsynchronously(getInstance(), runnable);
    }
    public boolean isQueued(int taskID) {
        return getScheduler().isQueued(taskID);
    }
    public void cancel(int taskID) {
        if (isQueued(taskID)) {
            getScheduler().cancelTask(taskID);
        }
    }
    public void disable() {
        getScheduler().cancelTasks(getInstance());
    }
}