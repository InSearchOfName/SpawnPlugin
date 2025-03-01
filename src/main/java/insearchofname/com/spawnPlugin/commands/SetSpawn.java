package insearchofname.com.spawnPlugin.commands;

import insearchofname.com.spawnPlugin.SpawnPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import insearchofname.com.spawnPlugin.Msg;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class SetSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Msg.send(sender, "&cOnly players can set the spawn!");
            return true;
        } else if (!(sender.isOp())) {
            Msg.send(sender, "&cOnly admins can set the spawn!");
            return true;
        }

        Location spawnLocation = player.getLocation();

        // Save the spawn location in the plugin's config
        SpawnPlugin.getPlugin().getConfig().set("spawn.world", spawnLocation.getWorld().getName());
        SpawnPlugin.getPlugin().getConfig().set("spawn.x", spawnLocation.getX());
        SpawnPlugin.getPlugin().getConfig().set("spawn.y", spawnLocation.getY());
        SpawnPlugin.getPlugin().getConfig().set("spawn.z", spawnLocation.getZ());
        SpawnPlugin.getPlugin().getConfig().set("spawn.yaw", spawnLocation.getYaw());
        SpawnPlugin.getPlugin().getConfig().set("spawn.pitch", spawnLocation.getPitch());
        SpawnPlugin.getPlugin().saveConfig(); // Save to config file

        Msg.send(player, "&aCustom spawn location set!");

        return true;
    }
}

