package net.leonardo_dgs.pluginlib;

import net.leonardo_dgs.pluginlib.command.CommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Commands {

    private static final Constructor<PluginCommand> COMMAND_CONSTRUCTOR;
    private static final Field COMMAND_MAP_FIELD;
    private static final Field KNOWN_COMMANDS_FIELD;

    static
    {
        Constructor<PluginCommand> commandConstructor;
        try
        {
            commandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            commandConstructor.setAccessible(true);
        }
        catch (NoSuchMethodException e)
        {
            throw new RuntimeException(e);
        }
        COMMAND_CONSTRUCTOR = commandConstructor;

        Field commandMapField;
        try
        {
            commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
        COMMAND_MAP_FIELD = commandMapField;

        Field knownCommandsField;
        try
        {
            knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommandsField.setAccessible(true);
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        }
        KNOWN_COMMANDS_FIELD = knownCommandsField;
    }

    private static CommandMap getCommandMap()
    {
        try
        {
            return (CommandMap) COMMAND_MAP_FIELD.get(Bukkit.getServer().getPluginManager());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get CommandMap", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Command> getKnownCommandMap()
    {
        try
        {
            return (Map<String, Command>) KNOWN_COMMANDS_FIELD.get(getCommandMap());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not get known commands map", e);
        }
    }

    public static CommandBuilder<CommandSender> builder()
    {
        return new CommandBuilder<>();
    }

    public static PluginCommand register(Plugin plugin, String label, String... aliases)
    {
        return register(plugin, label, plugin.getDescription().getName().toLowerCase(), aliases);
    }

    public static PluginCommand register(Plugin plugin, String label, String fallbackPrefix, String... aliases)
    {
        return register(plugin, label, fallbackPrefix, Arrays.asList(aliases));
    }

    public static PluginCommand register(Plugin plugin, String label, List<String> aliases)
    {
        return register(plugin, label, plugin.getDescription().getName().toLowerCase(), aliases);
    }

    public static PluginCommand register(Plugin plugin, String label, String fallbackPrefix, List<String> aliases)
    {
        PluginCommand cmd = null;
        try
        {
            cmd = COMMAND_CONSTRUCTOR.newInstance(label, plugin);
            cmd.setAliases(aliases);
            getCommandMap().register(label, fallbackPrefix, cmd);
        }
        catch (InstantiationException | InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return cmd;
    }

    public static void unregister(Command command)
    {
        command.unregister(getCommandMap());
    }

}
