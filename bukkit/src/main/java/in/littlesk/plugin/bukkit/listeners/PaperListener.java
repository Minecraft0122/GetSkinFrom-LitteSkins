package in.littlesk.plugin.bukkit.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import in.littlesk.plugin.core.Core;
import in.littlesk.plugin.core.yggdrasil.RemoteGameProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.concurrent.TimeUnit;

public class PaperListener implements Listener {
    private final Core core;

    public PaperListener(Core core) {
        this.core = core;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            return;
        }

        PlayerProfile localProfile = event.getPlayerProfile();
        if (localProfile.getProperties().stream().anyMatch(property -> property.getName().equals("textures"))) {
            return;
        }

        String name = event.getName();
        RemoteGameProfile remoteProfile;

        try {
            remoteProfile = this.core.getGameProfileByName(name).get(10, TimeUnit.SECONDS).orElse(null);
            if (remoteProfile == null) {
                return;
            }
        } catch (Exception e) {
            return;
        }


        RemoteGameProfile.Property textureProperty = remoteProfile.getProperty("textures").orElse(null);
        if (textureProperty == null) {
            return;
        }

        localProfile.setProperty(new ProfileProperty(
                "textures",
                textureProperty.value,
                textureProperty.signature
        ));

        event.setPlayerProfile(localProfile);
    }
}
