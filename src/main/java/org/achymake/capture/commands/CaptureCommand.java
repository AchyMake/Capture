package org.achymake.capture.commands;

import org.achymake.capture.Capture;
import org.achymake.capture.data.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CaptureCommand implements CommandExecutor, TabCompleter {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    public CaptureCommand() {
        getInstance().getCommand("capture").setExecutor(this);
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(getMessage().addColor("&6" + getInstance().name() + "&f: " + getInstance().version()));
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                getInstance().reload();
                player.sendMessage(getMessage().addColor("&6" + getInstance().name() + "&f: reloaded"));
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 0) {
                consoleCommandSender.sendMessage(getInstance().name() + ": " + getInstance().version());
                return true;
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                getInstance().reload();
                consoleCommandSender.sendMessage(getInstance().name() + ": reloaded");
                return true;
            }
        }
        return false;
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        var commands = new ArrayList<String>();
        if (sender instanceof Player && args.length == 1) {
            commands.add("reload");
        }
        return commands;
    }
}