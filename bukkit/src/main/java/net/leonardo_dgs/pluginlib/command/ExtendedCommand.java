package net.leonardo_dgs.pluginlib.command;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Predicate;

final class ExtendedCommand {

    private ICommandHandler handler;
    private ITabCompleteHandler tabHandler;
    private ImmutableList<Predicate<ICommandContext<? extends CommandSender>>> predicates;

    ExtendedCommand(ICommandHandler handler, ITabCompleteHandler tabHandler, ImmutableList<Predicate<ICommandContext<? extends CommandSender>>> predicates)
    {
        this.handler = handler;
        this.tabHandler = tabHandler;
        this.predicates = predicates;
    }

    @SuppressWarnings("unchecked")
    void call(ICommandContext<? extends CommandSender> context)
    {
        for (Predicate<ICommandContext<? extends CommandSender>> predicate : this.predicates)
            if (!predicate.test(context))
                return;

        handler.handle(context);
    }

    @SuppressWarnings("unchecked")
    List<String> callTabCompleter(ICommandContext<? extends CommandSender> context)
    {
        for (Predicate<ICommandContext<? extends CommandSender>> predicate : this.predicates)
            if (!predicate.test(context))
                return null;

        return tabHandler.handle(context);
    }

}
