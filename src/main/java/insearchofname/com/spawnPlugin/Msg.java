package insearchofname.com.spawnPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {
    public static void send(CommandSender sender, String msg) {
        send(sender,msg,"&a");
    }

    public static void send(CommandSender sender, String msg, String prefix) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
    }
}
