package net.leonardo_dgs.pluginlib.command;

import java.util.List;

public interface ITabCompleteHandler<T> {

    List<String> handle(ICommandContext<?> c);

}
