package org.achymake.capture.handlers;

import org.achymake.capture.Capture;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class MaterialHandler {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    public Material get(String materialName) {
        return Material.valueOf(materialName.toUpperCase());
    }
    public ItemStack getItemStack(String materialName, int amount) {
        var material = get(materialName);
        if (material != null) {
            return new ItemStack(material, amount);
        } else return null;
    }
    public boolean isAir(ItemStack itemStack) {
        return itemStack == null || itemStack.getType().equals(this.get("air"));
    }
    public boolean isPickedUp(ItemStack itemStack) {
        var meta = itemStack.getItemMeta();
        if (meta != null) {
            return meta.getPersistentDataContainer().has(getInstance().getNamespacedKey("carry"));
        } else return false;
    }
    public ItemStack toSpawnEgg(Entity entity) {
        var egg = getItemStack(entity.getType() + "_spawn_egg", 1);
        var eggMeta = (SpawnEggMeta) egg.getItemMeta();
        if (eggMeta != null) {
            eggMeta.setSpawnedEntity(entity.createSnapshot());
            eggMeta.getPersistentDataContainer().set(getInstance().getNamespacedKey("carry"), PersistentDataType.BOOLEAN, true);
            var list = new ArrayList<String>();
            var lore = getInstance().getConfig().getString("spawn-egg-lore").replace("{entity}", getInstance().getEntityHandler().getName(entity));
            list.add(getInstance().getMessage().addColor(lore));
            eggMeta.setLore(list);
            egg.setItemMeta(eggMeta);
            return egg;
        } else return null;
    }
    public boolean isItem(ItemStack itemStack) {
        return itemStack.getType().equals(get(getInstance().getConfig().getString("item.type"))) && hasEnchantment(itemStack);
    }
    public boolean hasEnchantment(ItemStack itemStack) {
        var enchantment = getInstance().getConfig().getString("item.enchantment");
        if (enchantment.equalsIgnoreCase("none")) {
            return true;
        } else return itemStack.getItemMeta().hasEnchant(Enchantment.getByName(enchantment));
    }
}