package org.achymake.capture;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.achymake.capture.commands.*;
import org.achymake.capture.data.*;
import org.achymake.capture.handlers.*;
import org.achymake.capture.listeners.*;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public final class Capture extends JavaPlugin {
    private static Capture instance;
    private Message message;
    private EntityHandler entityHandler;
    private MaterialHandler materialHandler;
    private ScheduleHandler scheduleHandler;
    private WorldHandler worldHandler;
    private UpdateChecker updateChecker;
    private BukkitScheduler bukkitScheduler;
    private PluginManager pluginManager;
    private StateFlag CAPTURE_FLAG;
    public StateFlag getFlag() {
        return CAPTURE_FLAG;
    }
    @Override
    public void onLoad() {
        CAPTURE_FLAG = new StateFlag("capture", true);
        getWorldGuard().getFlagRegistry().register(getFlag());
    }
    @Override
    public void onEnable() {
        instance = this;
        message = new Message();
        entityHandler = new EntityHandler();
        materialHandler = new MaterialHandler();
        scheduleHandler = new ScheduleHandler();
        worldHandler = new WorldHandler();
        updateChecker = new UpdateChecker();
        bukkitScheduler = getServer().getScheduler();
        pluginManager = getServer().getPluginManager();
        commands();
        events();
        reload();
        sendInfo("Enabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
        getUpdateChecker().getUpdate();
    }
    @Override
    public void onDisable() {
        getScheduleHandler().disable();
        sendInfo("Disabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
    }
    private void commands() {
        new CaptureCommand();
    }
    private void events() {
        new PlayerInteract();
        new PlayerInteractEntity();
        new PlayerJoin();
        new PlayerLeashEntity();
        new PlayerPickEntity();
    }
    public void reload() {
        if (!(new File(getDataFolder(), "config.yml")).exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        } else reloadConfig();
        getMessage().reload();
    }
    public PluginManager getPluginManager() {
        return pluginManager;
    }
    public BukkitScheduler getBukkitScheduler() {
        return bukkitScheduler;
    }
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    public WorldHandler getWorldHandler() {
        return worldHandler;
    }
    public ScheduleHandler getScheduleHandler() {
        return scheduleHandler;
    }
    public MaterialHandler getMaterialHandler() {
        return materialHandler;
    }
    public EntityHandler getEntityHandler() {
        return entityHandler;
    }
    public Message getMessage() {
        return message;
    }
    public static Capture getInstance() {
        return instance;
    }
    public NamespacedKey getNamespacedKey(String key) {
        return new NamespacedKey(this, key);
    }
    public void sendInfo(String message) {
        getLogger().info(message);
    }
    public void sendWarning(String message) {
        getLogger().warning(message);
    }
    public String name() {
        return getDescription().getName();
    }
    public String version() {
        return getDescription().getVersion();
    }
    public String getMinecraftVersion() {
        return getServer().getBukkitVersion();
    }
    public String getMinecraftProvider() {
        return getServer().getName();
    }
    public WorldGuard getWorldGuard() {
        return WorldGuard.getInstance();
    }
}