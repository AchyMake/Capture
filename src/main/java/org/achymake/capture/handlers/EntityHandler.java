package org.achymake.capture.handlers;

import org.achymake.capture.Capture;
import org.achymake.capture.data.Message;
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
    private Message getMessage() {
        return getInstance().getMessage();
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
    public String getScale(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var scale = livingEntity.getAttribute(Attribute.GENERIC_SCALE);
            if (scale != null) {
                return getMessage().getFormatted(scale.getBaseValue());
            } else return null;
        } else return null;
    }
    public String getHealth(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return getMessage().getFormatted(livingEntity.getHealth());
        } else return null;
    }
    public String getProfession(Entity entity) {
        if (entity instanceof Villager villager) {
            return villager.getProfession().name();
        } else return null;
    }
    public String getJumpStrength(Entity entity) {
        if (entity instanceof AbstractHorse abstractHorse) {
            return getMessage().getFormatted(abstractHorse.getJumpStrength());
        } else return null;
    }
    public String getColor(Entity entity) {
        return switch (entity) {
            case Axolotl axolotl -> getMessage().toTitleCase(axolotl.getVariant().name());
            case Cat cat -> getMessage().toTitleCase(cat.getCatType().name());
            case Horse horse -> getMessage().toTitleCase(horse.getColor().name());
            case Llama llama -> getMessage().toTitleCase(llama.getColor().name());
            case Parrot parrot -> getMessage().toTitleCase(parrot.getVariant().name());
            case Wolf wolf -> getMessage().toTitleCase(wolf.getVariant().getKey().getKey());
            case null, default -> null;
        };
    }
}