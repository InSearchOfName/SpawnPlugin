package insearchofname.com.spawnPlugin.commands;

import static insearchofname.com.spawnPlugin.SpawnPlugin.plugin;

public class CommandManager {
    public void registerCommands() {
        plugin.getCommand("spawn").setExecutor(new Spawn());
        plugin.getCommand("setspawn").setExecutor(new SetSpawn());
    }
}
