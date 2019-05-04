package net.leomixer17.pluginlib.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public final class Players {

    public static void sendMessage(String[] messages, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            for (String msg : messages)
                sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', msg)), sender);
    }

    public static void sendMessage(String message, CommandSender... senders)
    {
        sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)), senders);
    }

    public static void sendMessage(BaseComponent message, CommandSender... senders)
    {
        sendMessage(new BaseComponent[]{message}, senders);
    }

    public static void sendMessage(BaseComponent[] message, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            sender.sendMessage(message);
    }

}
