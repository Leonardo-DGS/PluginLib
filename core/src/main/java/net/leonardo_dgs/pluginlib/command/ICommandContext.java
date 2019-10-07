package net.leonardo_dgs.pluginlib.command;

import com.google.common.collect.ImmutableList;

public interface ICommandContext<T> {

    T sender();

    void reply(String... message);

    String label();

    ImmutableList<String> args();

    default String arg(int index)
    {
        if (index < 0 || index >= args().size())
            return null;
        return args().get(index);
    }

    default String cursor()
    {
        return String.join(" ", args());
    }

}
