package smily.animate.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import smily.animate.core.command.GroupManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandGroupSuggestion implements TabCompleter {

    private final String[] args1 = {"execute", "info", "selectName", "selectID", "change", "delete", "new", "deselect", "repeat"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tab = new ArrayList<>();

        switch (args.length){

            case 1 -> {
                if(args[0].isEmpty()){
                    return Arrays.stream(args1).toList();
                } else if (args[0].charAt(0) == '@'){
                    tab.addAll(GroupManager.nameList());
                    return tab;
                }
                tab = filterWordMatch(this.args1, args[0]);
            }

            case 2 -> {
                switch (args[0]){
                    case "info", "selectName" -> {
                        if (args[1].matches("\\d")) tab = GroupManager.idList().stream().map(id -> Integer.toString(id)).collect(Collectors.toList());
                        else tab = filterWordMatch(GroupManager.nameList(), args[1]);
                    }

                    case "change" -> {
                        if(Pattern.compile("\\d").matcher(args[1].substring(args[1].length()-1)).matches()){
                            tab.add(",");
                            tab.add("->");
                        }
                    }

                    case "delete" -> {
                        if(Pattern.compile("\\d").matcher(args[1].substring(args[1].length()-1)).matches()){
                            tab.add(",");
                        }
                    }

                    case "selectId" -> tab = GroupManager.idList().stream().map(Object::toString).collect(Collectors.toList());

                    case  "execute" -> {
                        tab = GroupManager.nameList();
                        tab.add("repeat");
                        tab = filterWordMatch(GroupManager.nameList(), args[1]);
                    }
                }
            }

        }
        return tab;
    }

    //filter the suggestion suggested list based on the inputed word by user
    public static List<String> filterWordMatch(String[] args, String match){
        Pattern pattern = Pattern.compile(match);

        if(match.isEmpty()) return Arrays.stream(args).toList();
        else return Arrays.stream(args).filter(st -> pattern.matcher(st).find()).collect(Collectors.toList());
    }

    public static List<String> filterWordMatch(List<String> args, String match){
        Pattern pattern = Pattern.compile(match);

        if(match.isEmpty()) return args;
        return args.stream().filter(st -> pattern.matcher(st).find()).collect(Collectors.toList());
    }
}