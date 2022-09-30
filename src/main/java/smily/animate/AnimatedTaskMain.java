package smily.animate;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import smily.animate.command.CommandGroupCommand;
import smily.animate.command.CommandGroupSuggestion;
import smily.animate.util.PluginProp;

public final class AnimatedTaskMain extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(PluginProp.playerCommandListener, this);
        getCommand("cmdg").setExecutor(new CommandGroupCommand());
        getCommand("cmdg").setTabCompleter(new CommandGroupSuggestion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
