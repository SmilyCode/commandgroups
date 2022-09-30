package smily.animate.util;

import org.bukkit.ChatColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandSenderUtilityTest {


    @Test
    void sendInfoWithHighlight() {
        System.out.println(info("Hi my name is Lukman", "\\s", "Lukman"));
    }

    public String info(String msg, String splitter, String... highlight){
        String splitRegex = "\\s";
        String[] s = msg.split(splitRegex);
        int index = 0;
        String result = "";

        for (String value : s) {
            if (index + 1 != highlight.length) {
                if (value.equals(highlight[index])) {
                    result = result + " " + ChatColor.YELLOW + " " + highlight[index] + " " + ChatColor.BLUE;
                    index++;
                }
            } else {
                result += value + " ";
            }
        }

        return result;
    }
}