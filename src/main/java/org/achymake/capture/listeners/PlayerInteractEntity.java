package org.achymake.capture.listeners;

import org.achymake.capture.Capture;
import org.achymake.capture.events.PlayerPickEntityEvent;
import org.achymake.capture.handlers.EntityHandler;
import org.achymake.capture.handlers.MaterialHandler;
import org.achymake.capture.handlers.WorldHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class PlayerInteractEntity implements Listener {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private MaterialHandler getMaterials() {
        return getInstance().getMaterialHandler();
    }
    private EntityHandler getEntityHandler() {
        return getInstance().getEntityHandler();
    }
    private WorldHandler getWorldHandler() {
        return getInstance().getWorldHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public PlayerInteractEntity() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        var player = event.getPlayer();
        var mainHand = player.getInventory().getItemInMainHand();
        var offHand = player.getInventory().getItemInOffHand();
        if (getMaterials().isPickedUp(mainHand) || getMaterials().isPickedUp(offHand)) {
            event.setCancelled(true);
        } else {
            var entity = event.getRightClicked();
            if (!player.isSneaking())return;
            if (entity instanceof Player)return;
            if (!getEntityHandler().isEnable(entity))return;
            if (!player.hasPermission("capture.event.capture." + entity.getType().toString().toLowerCase()))return;
            if (player.hasCooldown(mainHand.getType()))return;
            if (entity.isInvulnerable())return;
            if (entity.isInsideVehicle())return;
            if (!getWorldHandler().isAllowedCarry(entity.getLocation()))return;
            if (getEntityHandler().isLeashed(entity))return;
            if (!getMaterials().isItem(mainHand))return;
            if (!getMaterials().isAir(offHand))return;
            var owner = getEntityHandler().getOwner(entity);
            if (owner == null || owner == player) {
                event.setCancelled(true);
                getPluginManager().callEvent(new PlayerPickEntityEvent(player, entity));
            } else player.sendMessage(getInstance().getMessage().get("error.entity.tamed")
                    .replace("{entity}", getEntityHandler().getName(entity))
                    .replace("{owner}", Objects.requireNonNullElse(owner.getName(), "Null")));
        }
    }
}