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
    public double getScale(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            var scale = livingEntity.getAttribute(Attribute.SCALE);
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
    public String getJumpStrength(Entity entity) {
        return switch (entity) {
            case CamelHusk camelHusk -> getMessage().getFormatted(camelHusk.getJumpStrength());
            case Camel camel -> getMessage().getFormatted(camel.getJumpStrength());
            case Donkey donkey -> getMessage().getFormatted(donkey.getJumpStrength());
            case Mule mule -> getMessage().getFormatted(mule.getJumpStrength());
            case Horse horse -> getMessage().getFormatted(horse.getJumpStrength());
            case SkeletonHorse skeletonHorse -> getMessage().getFormatted(skeletonHorse.getJumpStrength());
            case ZombieHorse zombieHorse -> getMessage().getFormatted(zombieHorse.getJumpStrength());
            case null, default -> null;
        };
    }
    public String getColor(Entity entity) {
        return switch (entity) {
            case Axolotl axolotl -> getMessage().toTitleCase(axolotl.getVariant().name());
            case Cat cat -> getMessage().toTitleCase(cat.getCatType().name());
            case CamelHusk camelHusk -> getMessage().toTitleCase(camelHusk.getVariant().name());
            case Camel camel -> getMessage().toTitleCase(camel.getVariant().name());
            case Donkey donkey -> getMessage().toTitleCase(donkey.getVariant().name());
            case Llama llama -> getMessage().toTitleCase(llama.getVariant().name());
            case Parrot parrot -> getMessage().toTitleCase(parrot.getVariant().name());
            case Wolf wolf -> getMessage().toTitleCase(wolf.getVariant().toString());
            case Mule mule -> getMessage().toTitleCase(mule.getVariant().name());
            case Horse horse -> getMessage().toTitleCase(horse.getVariant().name());
            case SkeletonHorse skeletonHorse -> getMessage().toTitleCase(skeletonHorse.getVariant().name());
            case ZombieHorse zombieHorse -> getMessage().toTitleCase(zombieHorse.getVariant().name());
            case null, default -> null;
        };
    }
}