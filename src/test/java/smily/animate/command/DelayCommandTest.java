package smily.animate.command;

import org.junit.jupiter.api.Test;
import smily.animate.util.TickUtility;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class DelayCommandTest {

    @Test
    public void canGetValidFormat(){
//        assertTrue(DelayCommand.isFormatAreValid(new String[]{"3s"}));
        if (!DelayCommand.areFormatValid(new String[]{"3s"})){
            System.out.println("Time format you inputed is not correct.");
        }
    }

    @Test
    public void testValidFormat(){
        assertTrue(Pattern.compile(TickUtility.timeRegex).matcher("2m").matches());
    }

    @Test
    public void isCorrectCommand(){
        String str = "/delay 3s 4s";
        System.out.println(str.substring(7));
    }
}