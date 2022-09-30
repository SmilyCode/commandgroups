package smily.animate.core.select;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Select<T> {
    private Map<CommandSender, T> selected = new HashMap<>();

    public void storeSelected(CommandSender sender, T obj){
        selected.put(sender, obj);
    }

    public void removeSelected(CommandSender sender){
        selected.remove(sender);
    }

    public void changeSelected(CommandSender sender, T obj){
        removeSelected(sender);
        storeSelected(sender, obj);
    }

    public T getSelected(CommandSender sender){
        return selected.get(sender);
    }

    public boolean containSender(CommandSender sender){
        return selected.containsKey(sender);
    }
}
