package smily.animate.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import smily.animate.util.Dummy;

public class Command {
    private final String command;

    public Command(String command){
        this.command = command;
    }

    public synchronized void execute(CommandSender sender){
        Bukkit.dispatchCommand(sender, command.substring(1));
    }

    public String getCommand(){
        return command;
    }
}
