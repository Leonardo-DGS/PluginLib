package net.leomixer17.pluginlib.nms;

import net.leomixer17.pluginlib.util.Players;
import org.bukkit.entity.Player;

public class NMSPlayer {

    private final Player player;

    public NMSPlayer(final Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public void sendMessage(final String... messages)
    {
        Players.msg(this.getPlayer(), messages);
    }

    public void sendTitle(final Integer fadeIn, final Integer stay, final Integer fadeOut, final String title, final String subtitle)
    {
        Players.sendTitle(fadeIn, stay, fadeOut, title, subtitle, this.getPlayer());
    }

    public void sendActionBar(final String text)
    {
        Players.sendActionBar(text, this.getPlayer());
    }

    public void clearActionBar()
    {
        this.sendActionBar("");
    }

    public void clearTitle()
    {
        this.sendTitle(0, 0, 0, "", "");
    }

    public void sendTabTitle(String header, String footer)
    {
        Players.sendTabTitle(header, footer, this.getPlayer());
    }

    public int getPing()
    {
        return Players.getPing(this.getPlayer());
    }

}
