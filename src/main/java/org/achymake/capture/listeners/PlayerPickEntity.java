package org.achymake.capture.listeners;

import org.achymake.capture.Capture;
import org.achymake.capture.events.PlayerPickEntityEvent;
import org.achymake.capture.handlers.MaterialHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class PlayerPickEntity implements Listener {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private MaterialHandler getMaterials() {
        return getInstance().getMaterialHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public PlayerPickEntity() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerPickEntity(PlayerPickEntityEvent event) {
        if (event.isCancelled())return;
        var entity = event.getRightClicked();
        var player = event.getPlayer();
        var egg = getMaterials().toSpawnEgg(entity);
        if (egg == null)return;
        player.getInventory().setItemInOffHand(egg);
        var itemStack = player.getInventory().getItemInMainHand();
        player.setCooldown(itemStack.getType(), 20);
        entity.remove();
    }
}