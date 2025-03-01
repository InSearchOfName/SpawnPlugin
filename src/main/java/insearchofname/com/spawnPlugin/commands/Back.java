package insearchofname.com.spawnPlugin.commands;

import insearchofname.com.spawnPlugin.Msg;
import insearchofname.com.spawnPlugin.SpawnPlugin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class Back implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) {
            Msg.send(commandSender, "&cYou must be a player to use this command!");
            return true;
        }

        if (Spawn.getLastLocation(player) == null) {
            Msg.send(player, "&cYou have no previous location to return to.");
            return true;
        }

        startTeleportTimer(player);
        return true;

    }

    private void startTeleportTimer(Player player) {
        Location initialLocation = player.getLocation();
        new BukkitRunnable() {
            int countdown = 5;

            @Override
            public void run() {
                if (countdown <= 0) {
                    // teleports player back
                    player.teleport(Spawn.getLastLocation(player));
                    Spawn.removeLastLocation(player);
                    Msg.send(player, "&aTeleported back!");
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
                Msg.send(player, "&eTeleporting back in: &9" + countdown + " seconds. &eDo not move!");
                countdown--;
            }
        }.runTaskTimer(SpawnPlugin.getPlugin(), 0L, 20L); // Runs every 20 ticks (1 second)
    }
}
