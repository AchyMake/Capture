Check Repository = https://jitpack.io/#AchyMake/Capture
```xml
<repositories>
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.AchyMake</groupId>
        <artifactId>Capture</artifactId>
        <version>LATEST</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
Example

```java
package org.example.yourplugin;

import org.achymake.carry.events.PlayerPickEntityEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerPickEntity implements Listener {
    private YourPlugin getInstance() {
        return YourPlugin.getInstance();
    }

    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }

    public PlayerPickEntity() {
        getPluginManager().registerEvents(this, getInstance());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerPickEntity(PlayerPickEntityEvent event) {
        var player = event.getPlayer();
        var rightClicked = event.getRightClicked();
        //cancel event if you want
        event.setCancelled(true);
    }
}

```