package org.achymake.capture.data;

import org.achymake.capture.Capture;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;

public class Message {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private final File file = new File(getInstance().getDataFolder(), "message.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public String get(String path) {
        return config.isString(path) ? addColor(config.getString(path)) : path + ": is missing a value";
    }
    public String get(String path, String... format) {
        return config.isString(path) ? addColor(MessageFormat.format(config.getString(path), format)) : path + ": is missing a value";
    }
    public String getFormatted(double value) {
        return new DecimalFormat("#,##0.00").format(value);
    }
    private void setup() {
        config.options().copyDefaults(true);
        config.set("error.entity.tamed", "{entity}&c is tamed by&f {owner}");
        try {
            config.save(file);
        } catch (IOException e) {
            getInstance().sendWarning(e.getMessage());
        }
    }
    public void reload() {
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else setup();
    }
    public String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public String toTitleCase(String string) {
        if (string.contains(" ")) {
            var builder = getBuilder();
            for(var strings : string.split(" ")) {
                builder.append(strings.toUpperCase().charAt(0)).append(strings.substring(1).toLowerCase());
                builder.append(" ");
            }
            return builder.toString().strip();
        } else if (string.contains("_")) {
            var builder = getBuilder();
            for(var strings : string.split("_")) {
                builder.append(strings.toUpperCase().charAt(0)).append(strings.substring(1).toLowerCase());
                builder.append(" ");
            }
            return builder.toString().strip();
        } else return string.toUpperCase().charAt(0) + string.substring(1).toLowerCase();
    }
    public StringBuilder getBuilder() {
        return new StringBuilder();
    }
}