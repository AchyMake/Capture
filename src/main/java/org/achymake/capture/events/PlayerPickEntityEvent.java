package org.achymake.capture.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jspecify.annotations.NonNull;

public class PlayerPickEntityEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Entity rightClicked;
    private boolean cancelled;
    public PlayerPickEntityEvent(Player player, Entity rightClicked) {
        this.player = player;
        this.rightClicked = rightClicked;
    }
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
    public Player getPlayer() {
        return player;
    }
    public Entity getRightClicked() {
        return rightClicked;
    }
    public boolean isCancelled() {
        return cancelled;
    }
    public @NonNull HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}