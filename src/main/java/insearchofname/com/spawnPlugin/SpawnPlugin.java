package insearchofname.com.spawnPlugin;

import insearchofname.com.spawnPlugin.commands.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpawnPlugin extends JavaPlugin {
    public static SpawnPlugin plugin;
    public static ConsoleCommandSender console;

    @Override
    public void onEnable() {
        plugin = this;
        console = Bukkit.getServer().getConsoleSender();


        new CommandManager().registerCommands();
        saveDefaultConfig();
        console.sendMessage(ChatColor.GREEN + "Spawn Plugin Enabled");
    }

    @Override
    public void onDisable() {
        console.sendMessage(ChatColor.RED + "Spawn Plugin Disabled");
    }

    public static SpawnPlugin getPlugin() {
        return plugin;
    }

    public static ConsoleCommandSender getConsole() {
        return console;
    }
}
