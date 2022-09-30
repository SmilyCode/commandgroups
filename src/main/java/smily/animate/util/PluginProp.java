package smily.animate.util;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.plugin.Plugin;
import smily.animate.core.command.PlayerCommandListener;

import java.util.regex.Pattern;

public class PluginProp {
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("CommandGroup");
    public static final PlayerCommandListener playerCommandListener = new PlayerCommandListener();
    public static final String[] syntaxes = new String[]{"new", "execute", "deselect", "repeat"};
    public static final Pattern patternMultIndex = Pattern.compile("((\\d+),?)+");
    public static final Pattern patternSwapIndex = Pattern.compile("(\\d+)->(\\d+)");

}