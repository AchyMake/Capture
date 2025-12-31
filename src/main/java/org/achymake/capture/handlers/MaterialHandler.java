package org.achymake.capture.handlers;

import org.achymake.capture.Capture;
import org.achymake.capture.data.Message;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
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
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    private EntityHandler getEntityHandler() {
        return getInstance().getEntityHandler();
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    private NamespacedKey getNamespacedKey(String key) {
        return getInstance().getNamespacedKey(key);
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
            return meta.getPersistentDataContainer().has(getNamespacedKey("carry"));
        } else return false;
    }
    public ItemStack toSpawnEgg(Entity entity) {
        var egg = getItemStack(entity.getType() + "_spawn_egg", 1);
        if (egg != null) {
            var eggMeta = (SpawnEggMeta) egg.getItemMeta();
            if (eggMeta != null) {
                eggMeta.setSpawnedEntity(entity.createSnapshot());
                eggMeta.getPersistentDataContainer().set(getNamespacedKey("carry"), PersistentDataType.BOOLEAN, true);
                var name = getEntityHandler().getName(entity);
                var health = getMessage().getFormatted(getEntityHandler().getHealth(entity));
                var scale = getMessage().getFormatted(getEntityHandler().getScale(entity));
                var stringList = new ArrayList<String>();
                stringList.add(getMessage().addColor("&9Name&f: " + name));
                stringList.add(getMessage().addColor("&9Health&f: " + health));
                stringList.add(getMessage().addColor("&9Scale&f: " + scale));
                var profession = getEntityHandler().getProfession(entity);
                if (profession != null) {
                    stringList.add(getMessage().addColor("&9Profession&f: " + getMessage().toTitleCase(profession)));
                }
                eggMeta.setLore(stringList);
                egg.setItemMeta(eggMeta);
                return egg;
            } else return null;
        } else return null;
    }
    public boolean isItem(ItemStack itemStack) {
        return itemStack.getType().equals(get(getConfig().getString("item.type"))) && hasEnchantment(itemStack);
    }
    public boolean hasEnchantment(ItemStack itemStack) {
        var enchantment = getConfig().getString("item.enchantment");
        if (enchantment.equalsIgnoreCase("none")) {
            return true;
        } else return itemStack.getItemMeta().hasEnchant(Enchantment.getByName(enchantment));
    }
}