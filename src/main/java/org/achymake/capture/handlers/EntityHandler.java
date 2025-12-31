package org.achymake.capture.handlers;

import org.achymake.capture.Capture;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;

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
    public double getScale(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var scale = livingEntity.getAttribute(Attribute.GENERIC_SCALE);
            if (scale != null) {
                return scale.getBaseValue();
            } else return 1.0;
        } else return 1.0;
    }
    public double getHealth(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.getHealth();
        } else return 0.0;
    }
    public String getProfession(Entity entity) {
        if (entity instanceof Villager villager) {
            return villager.getProfession().name();
        } else return null;
    }
}