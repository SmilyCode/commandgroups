package smily.animate.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import smily.animate.command.DelayCommand;
import smily.animate.core.select.Database;
import smily.animate.util.CommandSenderUtility;
import smily.animate.util.PluginProp;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
* NOTE!!
* The index of the commands did not start from 0 rather from 1, so it's friendly for the end user.
* It could be just converted later but that would be confusing for later development.
* */

public class CommandGroup {
//    private TreeMap<Integer, Command> commands;
    private List<Command> commands;
    private Database<Stage> stages = new Database<>();
    private final int id;
    private final String name;
    private int num2 = 1;
    private boolean isRepeated;
    private Integer repeatedTimes;
    private int num3 = 0;
    private boolean isStopped;

    protected CommandGroup(List<Command> commands, String name, int id){
        this.commands = commands;
        this.id = id;
        this.name = name;
    }

    protected CommandGroup(String name, int id){
        this(new ArrayList<>(), name, id);
    }

    public void execute(CommandSender sender, int from, int to){
        int delay = 0;
        int index = 0;

        //for stopping repeat with set times
        if(repeatedTimes != null) {
            if (repeatedTimes == 0) {
                isRepeated = false;
                repeatedTimes = null;
            } else if (isStopped) {
                CommandSenderUtility.sendInfo(sender, "Stopped executing");
                isStopped = false;
                return;
            }
        }

        for (int i = from; i <= to; i++) {
            Command command = commands.get(i-1);

            //break whenever there's delay command
            if(command.getCommand().split("\\s")[0].equals("/delay")){
                delay = DelayCommand.onCommand(sender, command.getCommand().substring(7).split("\\s"));
                index = i;
                break;
            }

            command.execute(sender);

            //Send feedback when done executing all command
            if(i==commands.size()){
                CommandSenderUtility.sendInfo(sender, "Successfully executed all command.");
            }
        }

        //to start the delay with Bukkit Scheduler
        if(delay != 0){
            int finalIndex = index;

            if(finalIndex+1 >= commands.size()){
                if(isRepeated){
                    //a check to prevent error
                    if(repeatedTimes!=null) repeatedTimes--;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PluginProp.plugin, ()-> execute(sender), delay);
                }
                return;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(PluginProp.plugin, ()-> execute(sender, finalIndex+1, to), delay);
        } else if(isRepeated){
            //a check to prevent error
            if(repeatedTimes!=null) repeatedTimes--;
            Bukkit.getScheduler().scheduleSyncDelayedTask(PluginProp.plugin,()-> execute(sender));
        }

    }

    public void execute(CommandSender sender){
        //for executing from start of index
        execute(sender, 1, commands.size());
    }


    public void addCommand(Command command){
        commands.add(command);
    }


    public void delete(Integer... index){
        AtomicInteger indexUtil = new AtomicInteger(0);
        Arrays.stream(index).sorted().forEach(num -> {
            if(num<=0) return;
            commands.remove(num-1-indexUtil.getAndIncrement());
        });
    }

    public void repeat(int times){
        this.setRepeated(true);
        this.repeatedTimes = times;
    }

    public void repeat(){
        this.setRepeated(true);
        this.repeatedTimes = null;
    }

    public void unRepeat(){
        this.setRepeated(false);
        this.repeatedTimes = null;
    }


    public void addCommand(Command command, int afterIndex){
        commands.add(afterIndex, command);
    }

    public void addCommand(String command, int afterIndex){
        commands.add(afterIndex, new Command(command));
    }

    public void addCommand(String command){
        addCommand(new Command(command));
    }

    public void addStage(){
//        stages.storeData(num3, new Stage(Database.fromDatabase(commands)));
        num3++;
    }

    public void changeCommand(int index, Command command){
        commands.remove(index-1);
        commands.add(index-1, command);
    }

    public void changeCommand(int index, String command){
        changeCommand(index, new Command(command));
    }

    public void swap(int a, int b){
        Command commandA = this.getCommand(a);
        Command commandB = this.getCommand(b);

        changeCommand(a, commandB);
        changeCommand(b, commandA);
    }

    public Command getCommand(int index){
        return commands.get(index-1);
    }

    public int getSize(){
        return commands.size();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Command> getAllCommand(){
        AtomicInteger i = new AtomicInteger(1);
        Map<Integer, Command> r = new HashMap<>();

        commands.forEach(command -> r.put(i.getAndIncrement(), command));
        return r;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }


    class Stage{
        public final Database<Command> stageCommand;

        public Stage(Database<Command> commands){
            stageCommand = commands;
        }

        public int getSize(){
            return stageCommand.size();
        }

        public void addCommand(Command command){
            stageCommand.storeData(num2++, command);
        }

        public void addCommand(String command){
            addCommand(new Command(command));
        }

        public void deleteCommand(int index){
            stageCommand.removeData(index);
        }

//        public void changeCommand(int index, String command){
//            commands.put(index, new Command(command));
//        }
    }
}
