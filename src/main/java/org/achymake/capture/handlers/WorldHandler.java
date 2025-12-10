package org.achymake.capture.handlers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import org.achymake.capture.Capture;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class WorldHandler {
    private Capture getInstance() {
        return Capture.getInstance();
    }
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    private boolean isAllowed(ApplicableRegionSet applicableRegionSet) {
        for(var regionIn : applicableRegionSet) {
            var flag =  regionIn.getFlag(getInstance().getFlag());
            if (flag == StateFlag.State.ALLOW) {
                return true;
            } else if (flag == StateFlag.State.DENY) {
                return false;
            }
        }
        return true;
    }
    public boolean isAllowedCarry(Location location) {
        var world = location.getWorld();
        if (world != null) {
            if (getConfig().getStringList("worlds").contains(world.getName())) {
                var regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
                if (regionManager != null) {
                    var x = location.getX();
                    var y = location.getY();
                    var z = location.getZ();
                    var vector = BlockVector3.at(x, y, z);
                    var protectedCuboidRegion = new ProtectedCuboidRegion("_", vector, vector);
                    return !regionManager.getApplicableRegions(protectedCuboidRegion).getRegions().isEmpty() ? isAllowed(regionManager.getApplicableRegions(protectedCuboidRegion)) : true;
                } else return true;
            } else return false;
        } else return false;
    }
}