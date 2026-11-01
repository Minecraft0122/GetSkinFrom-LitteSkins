package in.littlesk.plugin.core.skinsrestorer;

import in.littlesk.plugin.core.yggdrasil.RemoteGameProfile;

import java.lang.reflect.Method;
import java.util.Optional;

/** Small reflective adapter so the plugin can use the installed SkinsRestorer API without bundling it. */
public final class SkinsRestorerBridge {
    private SkinsRestorerBridge() {
    }

    public static boolean applySkin(Object player, Class<?> playerType, RemoteGameProfile profile) {
        Optional<RemoteGameProfile.Property> texture = profile.getProperty("textures");
        if (!texture.isPresent()) {
            return false;
        }

        try {
            Class<?> provider = Class.forName("net.skinsrestorer.api.SkinsRestorerProvider");
            Object api = provider.getMethod("get").invoke(null);
            Class<?> apiType = Class.forName("net.skinsrestorer.api.SkinsRestorer");
            Object applier = apiType.getMethod("getSkinApplier", Class.class).invoke(api, playerType);

            Class<?> propertyType = Class.forName("net.skinsrestorer.api.property.SkinProperty");
            Object property = propertyType.getMethod("of", String.class, String.class)
                    .invoke(null, texture.get().value, texture.get().signature);

            Class<?> applierType = Class.forName("net.skinsrestorer.api.property.SkinApplier");
            Method applySkin = applierType.getMethod("applySkin", Object.class, propertyType);
            applySkin.invoke(applier, player, property);
            return true;
        } catch (ReflectiveOperationException | RuntimeException ignored) {
            return false;
        }
    }
}
