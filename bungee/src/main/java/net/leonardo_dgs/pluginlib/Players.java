package net.leonardo_dgs.pluginlib;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public final class Players {

    public static void msg(String[] messages, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            for (String msg : messages)
                msg(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', msg)), sender);
    }

    public static void msg(String message, CommandSender... senders)
    {
        msg(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)), senders);
    }

    public static void msg(CommandSender sender, String... messages)
    {
        msg(messages, sender);
    }

    public static void msg(BaseComponent message, CommandSender... senders)
    {
        msg(new BaseComponent[]{message}, senders);
    }

    public static void msg(BaseComponent[] message, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            sender.sendMessage(message);
    }

}
