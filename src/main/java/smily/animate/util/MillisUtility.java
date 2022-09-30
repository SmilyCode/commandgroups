package smily.animate.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MillisUtility {
    public static final String timeRegex = "(\\d+)([tsmh])";
    private static final Pattern timePattern = Pattern.compile(timeRegex);

    public static long fromTick(int tick){
        return 50L *tick;
    }

    public static long fromSecond(int second){
        return fromTick(20)*second;
    }

    public static long fromMinute(int minute){
        return fromSecond(60)*minute;
    }

    public static long fromHour(int hour){
        return fromMinute(60)*hour;
    }

    public static long fromFormat(String formattedTime) throws IllegalArgumentException{
        Matcher matcher = timePattern.matcher(formattedTime);

        if(isValidFormat(formattedTime)){
            matcher.find();

            int num = Integer.parseInt(matcher.group(1));
            switch (matcher.group(2)){
                case "t" -> {return fromTick(num);}
                case "s" -> {return fromSecond(num);}
                case "m" -> {return fromMinute(num);}
                case "h" -> {return fromHour(num);}
            }

        } else throw new IllegalArgumentException("The format you imported are incorrect.");

        return 0;
    }

    public static long fromManyFormat(String[] formattedTime){
        long result = 0;

        for(String s : formattedTime){
            result+= fromFormat(s);
        }

        return result;
    }
    public static boolean isValidFormat(String formattedTime){
        return timePattern.matcher(formattedTime).matches();
    }
}
