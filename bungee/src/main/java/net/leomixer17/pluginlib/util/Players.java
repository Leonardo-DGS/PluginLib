package net.leomixer17.pluginlib.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public final class Players {

    public static void msg(String[] msgs, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            for (String msg : msgs)
                msg(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', msg)), sender);
    }

    public static void msg(String msg, CommandSender... senders)
    {
        msg(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', msg)), senders);
    }

    public static void msg(BaseComponent msg, CommandSender... senders)
    {
        msg(new BaseComponent[]{msg}, senders);
    }

    public static void msg(BaseComponent[] msg, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            sender.sendMessage(msg);
    }

}
