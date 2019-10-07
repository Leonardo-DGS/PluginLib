package net.leonardo_dgs.pluginlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    private ExtendedCommand command;

    CommandHandler(ExtendedCommand command)
    {
        this.command = command;
    }

    @SuppressWarnings("unchecked")
    @Override
    public final boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        CommandContext<CommandSender> context = new CommandContext<>(sender, alias, args);

        this.command.call(context);
        return false;
    }

}
