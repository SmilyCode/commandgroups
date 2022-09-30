package smily.animate.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TickUtility {
    public static final String timeRegex = "(\\d+)([tsmh])";
    private static final Pattern timePattern = Pattern.compile(timeRegex);

    public static int fromTick(int tick){
        return tick;
    }

    public static int fromSecond(int second){
        return fromTick(20)*second;
    }

    public static int fromMinute(int minute){
        return fromSecond(60)*minute;
    }

    public static int fromHour(int hour){
        return fromMinute(60)*hour;
    }

    public static int fromFormat(String formattedTime) throws IllegalArgumentException{
        Matcher matcher = timePattern.matcher(formattedTime);

        if(isValidFormat(formattedTime)){
            matcher.find();

            int num = Integer.parseInt(matcher.group(1));
            switch (matcher.group(2)){
                case "t" -> {return num;}
                case "s" -> {return fromSecond(num);}
                case "m" -> {return fromMinute(num);}
                case "h" -> {return fromHour(num);}
            }

        } else throw new IllegalArgumentException("The format you imported are incorrect.");

        return 0;
    }

    public static int fromManyFormat(String[] formattedTime){
        int result = 0;

        for(String s : formattedTime){
            result+= fromFormat(s);
        }

        return result;
    }
    public static boolean isValidFormat(String formattedTime){
        return timePattern.matcher(formattedTime).matches();
    }
}
