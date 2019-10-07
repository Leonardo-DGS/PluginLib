package net.leonardo_dgs.pluginlib.command;

public interface ICommandHandler<T> {

    void handle(ICommandContext<T> c);

}
