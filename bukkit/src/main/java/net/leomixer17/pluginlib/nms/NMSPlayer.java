package net.leomixer17.pluginlib.nms;

import net.leomixer17.pluginlib.util.Players;
import org.bukkit.entity.Player;

public class NMSPlayer {

    private final Player player;

    public NMSPlayer(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public void sendMessage(String... messages)
    {
        Players.sendMessage(this.getPlayer(), messages);
    }

    public void sendTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
    {
        Players.sendTitle(fadeIn, stay, fadeOut, title, subtitle, this.getPlayer());
    }

    public void sendActionBar(String text)
    {
        Players.sendActionBar(text, this.getPlayer());
    }

    public void clearActionBar()
    {
        Players.clearActionBar(this.getPlayer());
    }

    public void clearTitle()
    {
        Players.clearTitle(this.getPlayer());
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
