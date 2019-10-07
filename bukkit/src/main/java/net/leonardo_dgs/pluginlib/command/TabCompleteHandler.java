package net.leonardo_dgs.pluginlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class TabCompleteHandler implements TabCompleter {

    private ExtendedCommand command;

    TabCompleteHandler(ExtendedCommand command)
    {
        this.command = command;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        CommandContext<CommandSender> context = new CommandContext<>(sender, alias, args);

        return this.command.callTabCompleter(context);
    }

}
