package org.achymake.capture.handlers;

import org.achymake.capture.Capture;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;

public class EntityHandler {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    public boolean isEnable(Entity entity) {
        return getConfig().getBoolean("entity." + entity.getType());
    }
    public boolean isLeashed(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.isLeashed();
        } else return false;
    }
    public String getName(Entity entity) {
        return entity.getCustomName() == null ? getInstance().getMessage().toTitleCase(entity.getType().toString()) : entity.getCustomName();
    }
    public AnimalTamer getOwner(Entity entity) {
        if (entity instanceof Tameable tameable) {
            if (tameable.isTamed() && tameable.getOwner() != null) {
                return tameable.getOwner();
            } else return null;
        } else return null;
    }
}