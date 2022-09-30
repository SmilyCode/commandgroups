package smily.animate.core.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class CommandGroupTest {
    CommandGroup commandGroup =  GroupManager.newGroup("Poop");

    @Test
    public void canAddCommand(){
        commandGroup.addCommand("summon cat");
        commandGroup.addCommand("tp SmilyKuy");

        commandGroup.getAllCommand().forEach((key, value) -> System.out.println(key + " " + value.getCommand()));
    }

    @Test
    public void canAddCommandAfterIndex(){
        commandGroup.addCommand("summon cat");
        commandGroup.addCommand("tp SmilyKuy");
        commandGroup.addCommand("tp 2");
        commandGroup.addCommand("tp 3");
        commandGroup.addCommand(new Command("tp 1"),4);

        commandGroup.getAllCommand().forEach((key, value) -> System.out.println(key + " " + value.getCommand()));
    }

    @Test
    public void canDeleteCommand(){
        commandGroup.addCommand("summon cat");
        commandGroup.addCommand("tp SmilyKuy");
        commandGroup.addCommand("kill");
        commandGroup.addCommand("undis");
        commandGroup.addCommand("f");
        commandGroup.addCommand("d");
        commandGroup.addCommand("a");

        commandGroup.getAllCommand().forEach((key, value) -> System.out.println(key + " " + value.getCommand()));
        System.out.println("");

        commandGroup.delete(3,6,2);

        System.out.println("");
        commandGroup.getAllCommand().forEach((key, value) -> System.out.println(key + " " + value.getCommand()));
    }

    @Test
    public void canChangeCommand(){
        commandGroup.addCommand("summon cat");
        commandGroup.addCommand("tp SmilyKuy");

        commandGroup.changeCommand(2, "killall mob");
        commandGroup.getAllCommand().forEach((key, value) -> System.out.println(key + " " + value.getCommand()));
    }

    @Test
    public void canSwapCommand(){
//        commandGroup.addCommand("summon cat");
//        commandGroup.addCommand("tp SmilyKuy");
//
//        commandGroup.swap(2, 1);
//        commandGroup.getAllCommand().forEach((key, value) -> System.out.println(key + " " + value.getCommand()));
        String command = "@hi".substring(1);

        Assertions.assertEquals("i", command);
    }
}