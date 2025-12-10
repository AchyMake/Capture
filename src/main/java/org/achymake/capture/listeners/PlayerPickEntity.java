package org.achymake.capture.listeners;

import org.achymake.capture.Capture;
import org.achymake.capture.data.Message;
import org.achymake.capture.events.PlayerPickEntityEvent;
import org.achymake.capture.handlers.EntityHandler;
import org.achymake.capture.handlers.MaterialHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class PlayerPickEntity implements Listener {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private EntityHandler getEntityHandler() {
        return getInstance().getEntityHandler();
    }
    private MaterialHandler getMaterials() {
        return getInstance().getMaterialHandler();
    }
    private Message getMessage() {
        return getInstance().getMessage();
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
        var owner = getEntityHandler().getOwner(entity);
        if (owner == null) {
            var egg = getMaterials().toSpawnEgg(entity);
            if (egg == null)return;
            player.getInventory().setItemInOffHand(egg);
            entity.remove();
        } else if (owner == player) {
            var egg = getMaterials().toSpawnEgg(entity);
            if (egg == null)return;
            player.getInventory().setItemInOffHand(egg);
            entity.remove();
        } else player.sendMessage(getMessage().get("error.entity.tamed")
                .replace("{entity}", getEntityHandler().getName(entity))
                .replace("{owner}", Objects.requireNonNullElse(owner.getName(), "Null")));
    }
}