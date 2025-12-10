package org.achymake.capture.listeners;

import org.achymake.capture.Capture;
import org.achymake.capture.handlers.MaterialHandler;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

public class PlayerInteract implements Listener {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private MaterialHandler getMaterials() {
        return getInstance().getMaterialHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public PlayerInteract() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var mainHand = player.getInventory().getItemInMainHand();
        var offHand = player.getInventory().getItemInOffHand();
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            var block = event.getClickedBlock();
            if (block == null)return;
            if (block.getState() instanceof CreatureSpawner) {
                if (getMaterials().isPickedUp(mainHand) || getMaterials().isPickedUp(offHand)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}