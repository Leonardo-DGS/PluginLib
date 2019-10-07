package net.leonardo_dgs.pluginlib.command;

import com.google.common.collect.ImmutableList;
import net.leonardo_dgs.pluginlib.Players;
import org.bukkit.command.CommandSender;

public class CommandContext<T extends CommandSender> implements ICommandContext {

    private final T sender;
    private final String label;
    private final ImmutableList<String> args;

    public CommandContext(T sender, String label, String[] args)
    {
        this.sender = sender;
        this.label = label;
        this.args = ImmutableList.copyOf(args);
    }

    public T sender()
    {
        return sender;
    }

    public void reply(String... message)
    {
        Players.msg(sender(), message);
    }

    public ImmutableList<String> args()
    {
        return args;
    }

    public String label()
    {
        return label;
    }

}
