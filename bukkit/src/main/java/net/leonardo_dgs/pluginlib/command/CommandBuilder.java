package net.leonardo_dgs.pluginlib.command;

import com.google.common.collect.ImmutableList;
import net.leonardo_dgs.pluginlib.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CommandBuilder<T extends CommandSender> {

    private ICommandHandler handler;
    private ITabCompleteHandler tabHandler;
    private final ImmutableList.Builder<Predicate<ICommandContext<? extends CommandSender>>> predicates;
    private String permission;
    private String permissionMessage;
    private String usage;
    private String description;
    private List<String> aliases;

    public CommandBuilder()
    {
        this.predicates = ImmutableList.builder();
    }

    private CommandBuilder(ICommandHandler handler, ITabCompleteHandler tabHandler, ImmutableList.Builder<Predicate<ICommandContext<? extends CommandSender>>> predicates, String permission, String permissionMessage, String usage, String description, List<String> aliases)
    {
        this.handler = handler;
        this.tabHandler = tabHandler;
        this.predicates = predicates;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
        this.usage = usage;
        this.description = description;
        this.aliases = aliases;
    }

    public void register(Plugin plugin, String label)
    {
        register(plugin, label, plugin.getDescription().getName().toLowerCase());
    }

    public void register(Plugin plugin, String label, String fallbackPrefix)
    {
        PluginCommand cmd = Commands.register(plugin, label, fallbackPrefix, aliases);
        Objects.requireNonNull(cmd);
        ExtendedCommand extendedCommand = new ExtendedCommand(handler, tabHandler, predicates.build());

        if (handler != null)
            cmd.setExecutor(new CommandHandler(extendedCommand));
        if (tabHandler != null)
            cmd.setTabCompleter(new TabCompleteHandler(extendedCommand));
        if (permission != null)
            cmd.setPermission(permission);
        if (permissionMessage != null)
            cmd.setPermissionMessage(permissionMessage);
        if (usage != null)
            cmd.setUsage(usage);
        if (description != null)
            cmd.setDescription(description);
    }

    public CommandBuilder<T> handler(ICommandHandler<T> handler)
    {
        this.handler = handler;
        return this;
    }

    public CommandBuilder<T> tabHandler(ITabCompleteHandler<T> tabHandler)
    {
        this.tabHandler = tabHandler;
        return this;
    }

    public CommandBuilder<T> assertCondition(Predicate<ICommandContext<? extends CommandSender>> predicate, String... failureMessage)
    {
        return assertCondition(context ->
        {
            if (predicate.test(context))
                return true;

            context.reply(failureMessage);
            return false;
        });
    }

    public CommandBuilder<T> assertCondition(Predicate<ICommandContext<? extends CommandSender>> predicate)
    {
        predicates.add(predicate);
        return this;
    }

    public CommandBuilder<Player> assertPlayer(String... failureMessage)
    {
        assertCondition(context -> context.sender() instanceof Player, failureMessage);
        return new CommandBuilder<>(handler, tabHandler, predicates, permission, permissionMessage, usage, description, aliases);
    }

    public CommandBuilder<ConsoleCommandSender> assertConsole(String... failureMessage)
    {
        assertCondition(context -> context.sender() instanceof ConsoleCommandSender, failureMessage);
        return new CommandBuilder<>(handler, tabHandler, predicates, permission, permissionMessage, usage, description, aliases);
    }

    public CommandBuilder<T> assertOp(String... failureMessage)
    {
        return assertCondition(context -> context.sender().isOp(), failureMessage);
    }

    public CommandBuilder<T> permission(String permission)
    {
        this.permission = permission;
        return this;
    }

    public CommandBuilder<T> permission(String permission, String failureMessage)
    {
        permission(permission);
        permissionMessage = failureMessage;
        return this;
    }

    public CommandBuilder<T> description(String description)
    {
        this.description = description;
        return this;
    }

    public CommandBuilder<T> usage(String usage)
    {
        this.usage = usage;
        return this;
    }

    public CommandBuilder<T> aliases(String... aliases)
    {
        this.aliases = ImmutableList.copyOf(aliases);
        return this;
    }

}
