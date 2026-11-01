package in.littlesk.plugin.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import in.littlesk.plugin.core.Core;
import in.littlesk.plugin.core.skinsrestorer.SkinsRestorerBridge;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class VelocityLittleSkinCommand implements SimpleCommand {
    private final Core core;

    public VelocityLittleSkinCommand(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player)) {
            invocation.source().sendMessage(Component.text("This command can only be used by a player."));
            return;
        }
        if (invocation.arguments().length != 1 || !invocation.arguments()[0].equalsIgnoreCase("update")) {
            invocation.source().sendMessage(Component.text("Usage: /littleskin update"));
            return;
        }

        Player player = (Player) invocation.source();
        player.sendMessage(Component.text("Fetching your LittleSkin skin..."));
        this.core.getGameProfileByName(player.getUsername()).orTimeout(10, TimeUnit.SECONDS).whenComplete((profile, error) -> {
            if (error != null || !profile.isPresent() || !SkinsRestorerBridge.applySkin(player, Player.class, profile.get())) {
                player.sendMessage(Component.text("LittleSkin did not provide a usable skin. Your SkinsRestorer skin was left unchanged."));
                return;
            }
            player.sendMessage(Component.text("Your LittleSkin skin has been applied."));
        });
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return invocation.arguments().length == 0 ? java.util.Collections.singletonList("update") : java.util.Collections.emptyList();
    }
}
