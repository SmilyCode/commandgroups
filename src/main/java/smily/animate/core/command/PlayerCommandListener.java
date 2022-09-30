package smily.animate.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import smily.animate.core.select.Selection;
import smily.animate.util.CommandSenderUtility;
import smily.animate.util.Dummy;

public class PlayerCommandListener implements Listener {
    private boolean isChanging = false;
    private int num = 0;
    private Integer[] index;
    private CommandGroup selectedGroup = null;

    @EventHandler
    public void listenForCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();

        if(Selection.commandGroup.containSender(player)){
            CommandGroup commandGroup = Selection.commandGroup.getSelected(player);
            String first = event.getMessage().split("\\s")[0];

            if(first.equals("/cmdg") || first.equals("/commandgroup:cmdg")) {
                event.setCancelled(false);
                return;
            } else if(isChanging){
                if(num<index.length) {
                    if(first.equals("/cancel") || first.equals("/")){
                        CommandSenderUtility.sendInfo(player, "Cancel current change");
                    } else {
                        selectedGroup.changeCommand(index[num], event.getMessage());
                        CommandSenderUtility.sendInfo(player, "Command changed");
                    }
                    num++;

                    changeCommandMsg(num, event.getPlayer());
                    event.setCancelled(true);
                    return;
                } else {
                    CommandSenderUtility.sendInfo(player, "Successfully change all commands");
                    selectedGroup = null;
                    isChanging = false;
                }
            }

            if(first.equals("/")){
                Selection.commandGroup.removeSelected(player);
                CommandSenderUtility.sendInfo(player, "Successfully deselected any group");
                event.setCancelled(true);
                return;
            }

            event.getPlayer().sendMessage( ChatColor.AQUA + "Added a command to " + commandGroup.getName() + ": " + ChatColor.YELLOW + event.getMessage());
            commandGroup.addCommand(new Command(event.getMessage()));
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }

    public void changeCommand(Integer[] indexes, CommandSender sender, CommandGroup commandGroup){
        this.index = indexes;
        isChanging = true;
        selectedGroup = commandGroup;
        num=0;
        changeCommandMsg(num, sender);
    }

    public void changeCommandMsg(int nums, CommandSender sender){
        if(nums<index.length){
            sender.sendMessage(ChatColor.AQUA +  "Input new command for " + ChatColor.YELLOW + index[nums] + ChatColor.AQUA + ":");
            CommandSenderUtility.sendComment(sender, "(current : " + selectedGroup.getCommand(index[num]).getCommand() + " )");
        }
    }
}
