package smily.animate.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandSenderUtility {
    private CommandSender commandSender;

    public CommandSenderUtility(CommandSender sender){
        this.commandSender = sender;
    }

    public void sendError(String msg){
        commandSender.sendMessage(ChatColor.RED + msg);
    }

    public void sendInfo(String msg){
        commandSender.sendMessage(ChatColor.AQUA + msg);
    }

    public void sendModify(String msg){
        commandSender.sendMessage(ChatColor.GREEN + msg);
    }

    public static void sendInfo(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.AQUA + msg);
    }

    public static void sendError(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.RED + msg);
    }

    public static void sendModify(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.GREEN + msg);
    }

    public static void sendComment(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.GRAY + msg);
    }

}
