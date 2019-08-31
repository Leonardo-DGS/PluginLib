package net.leomixer17.pluginlib.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;

public final class Commands {

    public static void registerCommand(String commandName, BukkitCommand command)
    {
        try
        {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(commandName, command);
        }
        catch (IllegalAccessException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }
}
