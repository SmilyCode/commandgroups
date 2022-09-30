package smily.animate.command;

import org.bukkit.command.CommandSender;
import smily.animate.util.CommandSenderUtility;
import smily.animate.util.TickUtility;

import java.util.Arrays;

//Added this command class for error checking
public class DelayCommand {
    public static int onCommand(CommandSender sender, String[] args) {
        CommandSenderUtility senderUtility = new CommandSenderUtility(sender);

        if(args.length == 0) {
            senderUtility.sendError("Wrong argument");
            return 0;
        }

        if (!areFormatValid(args)){
            senderUtility.sendError("Time format you inputed is not correct.");
            return 0;
        }

        int tick = TickUtility.fromManyFormat(args);

        senderUtility.sendInfo("Delayed command");
        return tick;
    }

    public static boolean areFormatValid(String[] args){
        return Arrays.stream(args).allMatch(TickUtility::isValidFormat);
    }
}
