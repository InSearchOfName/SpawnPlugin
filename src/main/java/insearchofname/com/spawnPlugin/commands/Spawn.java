package insearchofname.com.spawnPlugin.commands;

import insearchofname.com.spawnPlugin.Msg;
import insearchofname.com.spawnPlugin.SpawnPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Spawn implements CommandExecutor {

    private static final Map<String, Location> playerLastLocation = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            Msg.send(commandSender, "&cYou must be a player to use this command!");
            return true;
        }

        startTeleportTimer(player);
        return true;
    }

    private void storePlayerLocation(Player player) {
        // Store the player's current location (before teleportation) in a map using their UUID as the key
        playerLastLocation.put(player.getUniqueId().toString(), player.getLocation());
    }

    private void startTeleportTimer(Player player) {
        Location initialLocation = player.getLocation(); // Save initial location

        // Load custom spawn location from config
        String worldName = SpawnPlugin.getPlugin().getConfig().getString("spawn.world");
        double x = SpawnPlugin.getPlugin().getConfig().getDouble("spawn.x");
        double y = SpawnPlugin.getPlugin().getConfig().getDouble("spawn.y");
        double z = SpawnPlugin.getPlugin().getConfig().getDouble("spawn.z");


        if (worldName == null){
            Msg.send(player, "&cNo spawn set! Please use '/setspawn' to set one.");
            return;
        }
        World world = Bukkit.getWorld(worldName);


        Location customSpawnLocation = new Location(world, x, y, z);

        new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (countdown <= 0) {
                    // Teleport player to custom spawn
                    storePlayerLocation(player);
                    player.teleport(customSpawnLocation);
                    Msg.send(player, "&aTeleported to spawn!");
                    cancel();
                    return;
                }

                // Check if player moved
                if (!player.getLocation().equals(initialLocation)) {
                    Msg.send(player, "&cYou moved, you silly guy...");
                    cancel();
                    return;
                }

                // Send countdown message
                Msg.send(player, "&eTeleporting to spawn in: &9" + countdown + " seconds. &eDo not move!");
                countdown--;
            }
        }.runTaskTimer(SpawnPlugin.getPlugin(), 0L, 20L); // Runs every 20 ticks (1 second)
    }

    public static Location getLastLocation(Player player) {
        return playerLastLocation.get(player.getUniqueId().toString());
    }
    public static void removeLastLocation(Player player) {
        playerLastLocation.remove(player.getUniqueId().toString());
    }
}
