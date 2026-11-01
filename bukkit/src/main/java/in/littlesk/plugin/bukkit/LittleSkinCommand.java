package in.littlesk.plugin.bukkit;

import in.littlesk.plugin.core.Core;
import in.littlesk.plugin.core.skinsrestorer.SkinsRestorerBridge;
import in.littlesk.plugin.core.yggdrasil.RemoteGameProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class LittleSkinCommand implements CommandExecutor {
    private final BukkitMain plugin;
    private final Core core;

    public LittleSkinCommand(BukkitMain plugin, Core core) {
        this.plugin = plugin;
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by a player.");
            return true;
        }
        if (args.length != 1 || !args[0].equalsIgnoreCase("update")) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /littleskin update");
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage(ChatColor.GRAY + "Fetching your LittleSkin skin...");
        this.core.getGameProfileByName(player.getName()).whenComplete((profile, error) ->
                this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                    if (error != null || !profile.isPresent() || !SkinsRestorerBridge.applySkin(player, Player.class, profile.get())) {
                        player.sendMessage(ChatColor.RED + "LittleSkin did not provide a usable skin. Your SkinsRestorer skin was left unchanged.");
                        return;
                    }
                    player.sendMessage(ChatColor.GREEN + "Your LittleSkin skin has been applied.");
                }));
        return true;
    }
}
