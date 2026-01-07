package org.achymake.capture.listeners;

import org.achymake.capture.Capture;
import org.achymake.capture.handlers.MaterialHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerLeashEntity implements Listener {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private MaterialHandler getMaterials() {
        return getInstance().getMaterialHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public PlayerLeashEntity() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLeashEntity(PlayerLeashEntityEvent event) {
        var player = event.getPlayer();
        var entity = event.getEntity();
        if (entity instanceof Player)return;
        var mainHand = player.getInventory().getItem(event.getHand());
        if (mainHand == null)return;
        if (!getMaterials().isCaptureItemStack(mainHand))return;
        event.setCancelled(true);
    }
}