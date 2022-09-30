package smily.animate.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RegexTesting {

    @Test
    public void test(){
        //((\d+),?)+
        String regexIndexListing = "(poop)+";
        String example = "paooppaop";
        Matcher matcher = Pattern.compile(regexIndexListing).matcher(example);

        matcher.reset();
        while (matcher.find()){
            System.out.println(matcher.group(1));
        }

        matcher.reset();

        assertTrue(matcher.matches());
    }

    @Test
    public void test2(){
        String regexIndexListing = "(\\d+)->(\\d+)";
        String example = "1->2";
        Matcher matcher = Pattern.compile(regexIndexListing).matcher(example);

        matcher.reset();
        while (matcher.find()){
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }

        matcher.reset();

        assertTrue(matcher.matches());
    }

    public List<String> filterWordMatch(String[] args, String match){
        Pattern pattern = Pattern.compile(match);
        if(match.isEmpty()) throw new IllegalArgumentException("Word to be match cannot be empty or null");
        return Arrays.stream(args).filter(st -> pattern.matcher(st).find()).collect(Collectors.toList());
    }

    @Test
    public void test3(){
        String[] s = {"poop", "poppy", "car", "casing"};
        filterWordMatch(s, "poo").forEach(System.out::println);
    }
}
