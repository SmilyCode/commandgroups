package smily.animate.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Dummy{
    public static BlockCommandSender createDummy(Player player){
        return new BlockCommandSender() {
            @Override
            public Block getBlock() {
                return player.getWorld().getBlockAt(player.getLocation());
            }

            @Override
            public void sendMessage(String message) {

            }

            @Override
            public void sendMessage(String... messages) {

            }

            @Override
            public void sendMessage(UUID sender, String message) {

            }

            @Override
            public void sendMessage(UUID sender, String... messages) {

            }

            @Override
            public Server getServer() {
                return Bukkit.getServer();
            }

            @Override
            public String getName() {
                return new Random().toString();
            }

            @Override
            public Spigot spigot() {
                return Bukkit.getConsoleSender().spigot();
            }

            @Override
            public boolean isPermissionSet(String name) {
                return true;
            }

            @Override
            public boolean isPermissionSet(Permission perm) {
                return true;
            }

            @Override
            public boolean hasPermission(String name) {
                return true;
            }

            @Override
            public boolean hasPermission(Permission perm) {
                return true;
            }

            @Override
            public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
                return null;
            }

            @Override
            public PermissionAttachment addAttachment(Plugin plugin) {
                return null;
            }

            @Override
            public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
                return null;
            }

            @Override
            public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
                return null;
            }

            @Override
            public void removeAttachment(PermissionAttachment attachment) {

            }

            @Override
            public void recalculatePermissions() {

            }

            @Override
            public Set<PermissionAttachmentInfo> getEffectivePermissions() {
                return null;
            }

            @Override
            public boolean isOp() {
                return true;
            }

            @Override
            public void setOp(boolean value) {

            }
        };
    }

}
