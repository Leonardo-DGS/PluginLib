package net.leonardo_dgs.pluginlib.nms;

import net.leonardo_dgs.pluginlib.Players;
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
        Players.msg(this.getPlayer(), messages);
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        Players.sendTitle(title, subtitle, fadeIn, stay, fadeOut, this.getPlayer());
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
