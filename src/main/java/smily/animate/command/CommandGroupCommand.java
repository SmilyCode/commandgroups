package smily.animate.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import smily.animate.core.command.CommandGroup;
import smily.animate.core.command.GroupManager;
import smily.animate.core.select.Selection;
import smily.animate.util.CommandSenderUtility;
import smily.animate.util.Dummy;
import smily.animate.util.PluginProp;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandGroupCommand implements CommandExecutor {
    public final static Pattern namePattern = Pattern.compile("\\w.+");
    public final static Pattern idPattern = Pattern.compile("\\d+");
    private CommandSender sender;
    private CommandSenderUtility senderUtility;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.senderUtility = new CommandSenderUtility(sender);

        switch (args.length){
            case 0 -> senderUtility.sendError("Missing arguments" + ChatColor.RED);

            //Selecting a group
            case 1 -> {
                Matcher nameMatcher = namePattern.matcher(args[0]);
                Matcher idMatcher = idPattern.matcher(args[0]);

                //For args of length 1, the player has to select something
                if(nameMatcher.matches()) {
                    //Needed a sender check, sender that selecting one group
                    if (GroupManager.isNameExist(args[0])) {
                        Map<Integer, CommandGroup> commandGroupMap = GroupManager.getByName(args[0]);

                        assert commandGroupMap != null;
                        if(commandGroupMap.size() == 1){
                            Selection.commandGroup.changeSelected(sender, (CommandGroup) commandGroupMap.values().toArray()[0]);
                            senderUtility.sendInfo("Group named " + ChatColor.YELLOW + args[0] + ChatColor.AQUA + " selected.");
                            return true;
                        } else {
                            senderUtility.sendInfo("There's more than one group with the same name, please perform selection by id for specific one");
                            commandGroupMap.forEach((key, value) -> senderUtility.sendInfo("Group " + ChatColor.YELLOW + value.getName() + " has id " + ChatColor.YELLOW + key));
                            return false;
                        }
                    } else {
                        if(!isSelecting()) return false;
                        CommandGroup commandGroup = Selection.commandGroup.getSelected(sender);

                        switch (args[0]) {
                            case "deselect" -> {
                                Selection.commandGroup.removeSelected(sender);

                                senderUtility.sendInfo("Successfully deselect any group");
                                return true;
                            }

                            case "execute" -> {
                                commandGroup.execute(sender);
                                return true;
                            }

                            //Sending current command group information to Player
                            case "info" -> {
                                info(commandGroup);
                                return true;
                            }

                            case "change" -> {
                                senderUtility.sendModify("Select index of command you want to change:");
                                senderUtility.sendModify("");

                                listCommand(Selection.commandGroup.getSelected(sender));
                                return true;
                            }

                            case "repeat" -> {
                                if(commandGroup.isRepeated()){
                                    commandGroup.unRepeat();
                                    senderUtility.sendInfo("Cancel repeat");
                                } else {
                                    senderUtility.sendInfo("It will executes repeatedly when played");
                                    commandGroup.repeat();
                                }
                                return true;
                            }

                            default -> {
                                senderUtility.sendError("Wrong argument. See at github for more information");
                                return false;
                            }
                        }
                    }
                } else if (idMatcher.matches()){
                    int id = Integer.parseInt(args[0]);

                    if(GroupManager.isIdExist(id)){
                        CommandGroup commandGroup = GroupManager.getById(id);
                        Selection.commandGroup.changeSelected(sender, commandGroup);
                        senderUtility.sendInfo("Group named " + ChatColor.YELLOW + commandGroup.getName() + ChatColor.AQUA + " selected.");
                        return true;
                    } else {
                        senderUtility.sendError("Command group with that id doesn't exist");
                        return false;
                    }
                }


            }

            case 2 -> {
                switch (args[0]){
                    //Create new group
                    case "new" -> {
                        return newGroup(args[1]);
                    }

                    case "repeat" -> {
                        return repeat2(args[1]);
                    }

                    case "selectName" -> {
                        return selectName(args[1]);
                    }

                    case "selectID" -> {
                        return selectId(args[1]);
                    }

                    case "execute" -> {
                        if(args[1].equals(".")) return execute(Dummy.createDummy((Player) sender));
                        return execute2(args[1], sender);
                    }

                    case "change" -> {
                        if(!isSelecting()) return false;
                        return changeCommand(args[1]);
                    }

                    case "delete" -> {
                        return delete(args[1]);
                    }

                    case "info" -> {
                        return info2(args[1]);
                    }

                    default -> {
                        senderUtility.sendError("Wrong argument");
                        return false;
                    }
                }
            }

            case 3 -> {
                switch (args[0]){
                    case "execute" -> {
                        execute2(args[1], Bukkit.getPlayer(args[2]));
                        return false;
                    }

                    default -> {
                        senderUtility.sendError("Wrong argument");
                        return false;
                    }
                }

            }
        }

        return false;
    }

    private boolean info2(String args){
        if(!GroupManager.isNameExist(args)){
            senderUtility.sendError("Command Group doesn't exist");
            return false;
        }

        if(namePattern.matcher(args).matches()){
            Map<Integer, CommandGroup> commandGroupMap = Optional.ofNullable(GroupManager.getByName(args)).orElseThrow();

            if(commandGroupMap.size() == 1){
                commandGroupMap.values().forEach(this::info);
            } else {
                senderUtility.sendInfo("There's more than one group with the same name, please perform execution by id for specific one");
                commandGroupMap.forEach((key, value) -> sender.sendMessage("Group " + value.getName() + " has id " + key));
            }
            return true;
        } else if(idPattern.matcher(args).matches()){
            int id = Integer.parseInt(args);

            if(GroupManager.isIdExist(id)){
                info(GroupManager.getById(id));
                return true;
            } else {
                senderUtility.sendError("Command group with that id doesn't exist");
                return false;
            }

        } else {
            senderUtility.sendError("The inputed format are not correct. See at github for more information.");
            return false;
        }
    }

    private boolean info(CommandGroup commandGroup){
        senderUtility.sendModify("-------------------------------------------");
        senderUtility.sendInfo("Name: " + ChatColor.YELLOW + commandGroup.getName());
        senderUtility.sendInfo("ID: " + ChatColor.YELLOW + commandGroup.getId());
        senderUtility.sendInfo("Amount: " + ChatColor.YELLOW + commandGroup.getSize());
        sender.sendMessage("");

        senderUtility.sendInfo("All commands inside this group:");
        listCommand(commandGroup);
        senderUtility.sendModify("-------------------------------------------");
        return true;
    }

    public boolean isSelecting(){
        if(Selection.commandGroup.containSender(sender)) return true;

        senderUtility.sendError("No group was selected.");
        return false;
    }

    private void listCommand(CommandGroup commandGroup){
        commandGroup.getAllCommand().forEach((key, value) -> senderUtility.sendInfo(ChatColor.YELLOW + key.toString() + ". "  + ChatColor.AQUA + value.getCommand()));
    }


    private boolean changeCommand(String indexes){
        Matcher swapMatcher = PluginProp.patternSwapIndex.matcher(indexes);
        Matcher multMatcher = PluginProp.patternMultIndex.matcher(indexes);
        CommandGroup commandGroup = Selection.commandGroup.getSelected(sender);
        Map<Integer, smily.animate.core.command.Command> commands = commandGroup.getAllCommand();

        if(swapMatcher.matches()){
            swapMatcher.reset();

            int a = 0;
            int b = 0;

            while (swapMatcher.find()){
                a = Integer.parseInt(swapMatcher.group(1));
                b = Integer.parseInt(swapMatcher.group(2));
            }

            if(commands.containsKey(a) && commands.containsKey(b)){
                commandGroup.swap(a, b);
            } else {
                senderUtility.sendError("One of the index doesn't exist");
            }

        } else if (multMatcher.matches()) {
            String[] strings = indexes.split(",");
            Integer[] selection = Arrays.stream(strings).map(Integer::parseInt).toList().toArray(new Integer[strings.length]);

            PluginProp.playerCommandListener.changeCommand(Arrays.stream(selection).filter(integer ->
            {
                if (!commandGroup.getAllCommand().containsKey(integer)) {
                    senderUtility.sendError("Index " + integer + " doesn't exist in the group. Removing selection");
                    return false;
                }
                return true;
            }).toArray(Integer[]::new), sender, Selection.commandGroup.getSelected(sender));
        } else {
            senderUtility.sendError("Incorrect format. See github page for more information");
            return false;
        }

        return true;
    }

    private boolean repeat2(String args){
        if(!args.matches("\\d")){
            senderUtility.sendError("Use this /cmdg repeat <numberOfTimes>");
            return false;
        }

        int i = Integer.parseInt(args);
        Selection.commandGroup.getSelected(sender).repeat(i-1);
        senderUtility.sendInfo("It will executes repeatedly for " + i + " times");
        return true;
    }

    private boolean delete(String indexes){
        Matcher multMatcher = PluginProp.patternMultIndex.matcher(indexes);
        CommandGroup commandGroup = Selection.commandGroup.getSelected(sender);

        if (multMatcher.matches()) {
            String[] strings = indexes.split(",");

            commandGroup.delete(
                Arrays.stream(strings).map(Integer::parseInt).filter(integer -> {
                    if (!commandGroup.getAllCommand().containsKey(integer)) {
                        senderUtility.sendError("Index " + integer + " doesn't exist in the group. Removing selection");
                        return false;
                    }
                    return true;
                }
                ).toList().toArray(Integer[]::new)
            );

            senderUtility.sendInfo("Successfully delete commands");
            return true;
        } else {
            senderUtility.sendError("Incorrect format. See github page for more information");
            return false;
        }
    }

    private boolean execute2(String args, CommandSender sender){
        Matcher nameMatcher = namePattern.matcher(args);
        Matcher idMatcher = idPattern.matcher(args);

        if(nameMatcher.matches()) {
            if (GroupManager.isNameExist(args)) {
                Map<Integer, CommandGroup> commandGroupMap = Optional.ofNullable(GroupManager.getByName(args)).orElseThrow();

                if(commandGroupMap.size() == 1){
                    commandGroupMap.forEach((key, value) -> value.execute(sender));
                } else {
                    senderUtility.sendInfo("There's more than one group with the same name, please perform execution by id for specific one");
                    commandGroupMap.forEach((key, value) -> senderUtility.sendInfo("Group " + ChatColor.YELLOW + value.getName() + " has id " + ChatColor.YELLOW + key));
                }
                return true;
            } else {
                senderUtility.sendError("Name doesn't exist.");
                return false;
            }
        } else if(idMatcher.matches()){
            int id = Integer.parseInt(args);

            if(GroupManager.isIdExist(id)){
                GroupManager.getById(id).execute(sender);
                return true;
            } else {
                senderUtility.sendError("Command group with that id doesn't exist");
                return false;
            }
        } else {
            senderUtility.sendError("The inputed format are not correct. See at github for more information.");
            return false;
        }
    }

    private boolean execute(CommandSender sender){
        Selection.commandGroup.getSelected(this.sender).execute(sender);
        return true;
    }

    private boolean selectId(String args){
        Matcher idMatcher = idPattern.matcher(args);

        if(idMatcher.matches()){
            int id = Integer.parseInt(args);

            if(GroupManager.isIdExist(id)){
                CommandGroup commandGroup = GroupManager.getById(id);
                Selection.commandGroup.changeSelected(sender, commandGroup);
                senderUtility.sendInfo("Selected group name " + ChatColor.YELLOW + " " + commandGroup.getName());
                return true;
            } else {
                senderUtility.sendError("Command group with that id doesn't exist");
                return false;
            }
        } else {
            senderUtility.sendError("The inputed format are not correct. See at github for more information.");
            return false;
        }
    }

    private boolean selectName(String args){
        Matcher nameMatcher = namePattern.matcher(args);

        if(nameMatcher.matches()) {
            //Needed a sender check, sender that selecting one group
            if (GroupManager.isNameExist(args)) {
                Map<Integer, CommandGroup> commandGroupMap = Optional.ofNullable(GroupManager.getByName(args)).orElseThrow();

                if(commandGroupMap.size() == 1){
                    Selection.commandGroup.changeSelected(sender, (CommandGroup) commandGroupMap.entrySet().toArray()[0]);
                    return true;
                } else {
                    senderUtility.sendInfo("There's more than one group with the same name, please perform selection by id for spesific one");
                    commandGroupMap.forEach((key, value) -> sender.sendMessage("Group " + value.getName() + " has id " + key));
                    return false;
                }
            } else {
                senderUtility.sendError("Group doesn't exist.");
                return false;
            }
        } else {
            senderUtility.sendError("The inputed format are not correct. See at github for more information.");
            return false;
        }
    }

    private boolean newGroup(String args){
        Matcher nameMatcher = namePattern.matcher(args);

        if(nameMatcher.matches()) {
            if (Arrays.asList(PluginProp.syntaxes).contains(args)) {
                senderUtility.sendError("Name cannot be same as some of the plugin syntax");
                return false;
            } else if(GroupManager.isNameExist(args)){
                senderUtility.sendInfo("A group with this name already exist, it will still be created. But selecting it can only be using ID.");
            }

            CommandGroup commandGroup = GroupManager.newGroup(args);
            Selection.commandGroup.storeSelected(sender, commandGroup);
            senderUtility.sendInfo("Created command group named " + ChatColor.YELLOW + args + ChatColor.AQUA +" with ID " + ChatColor.YELLOW + commandGroup.getId());
            return true;
        } else {
            senderUtility.sendError("Number cannot be at the start of a name, See at github for more information.");
            return false;
        }
    }

}
