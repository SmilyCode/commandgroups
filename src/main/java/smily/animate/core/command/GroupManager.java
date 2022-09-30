package smily.animate.core.command;

import smily.animate.core.select.Databases;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupManager {
    private static int idNum = 0;

    public static Map<Integer, CommandGroup> getByName(String name){
        Map<Integer, CommandGroup> filtered = new HashMap<>();

        for(int i=0; i<Databases.commandGroup.size(); i++) {
            CommandGroup commandGroup = Databases.commandGroup.getDataFromId(i);
            if (commandGroup.getName().equals(name)) {
                filtered.put(commandGroup.getId(), commandGroup);
            }
        }

        if(filtered.size() != 0) return filtered;
        return null;
    }

    public static CommandGroup getById(int id){
        return Databases.commandGroup.getDataFromId(id);
    }

    public static boolean isNameExist(String name){
        return getByName(name) != null;
    }

    public static boolean isIdExist(int id){
        return Databases.commandGroup.containID(id);
    }

    public static CommandGroup newGroup(String name) throws IllegalArgumentException{

        CommandGroup commandGroup = new CommandGroup(name, idNum);
        Databases.commandGroup.storeData(idNum, commandGroup);
        idNum++;

        return commandGroup;
    }

    public static List<String> nameList(){
        return Databases.commandGroup.getMap().values().stream().map(CommandGroup::getName).toList();
    }

    public static Set<Integer> idList(){
        return Databases.commandGroup.getMap().keySet();
    }
}
