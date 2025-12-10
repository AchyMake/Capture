package org.achymake.capture.listeners;

import org.achymake.capture.Capture;
import org.achymake.capture.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerJoin implements Listener {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    private UpdateChecker getUpdateChecker() {
        return getInstance().getUpdateChecker();
    }
    public PlayerJoin() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        getUpdateChecker().getUpdate(event.getPlayer());
    }
}