package net.leomixer17.pluginlib.util;

import net.leomixer17.pluginlib.reflect.BukkitReflection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class Players {

    public static void sendTitle(Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle, Player... players)
    {
        try
        {
            Object e;
            Object chatTitle;
            Object chatSubtitle;
            Constructor<?> subtitleConstructor;
            Object titlePacket;
            Object subtitlePacket;

            if (title != null)
            {
                // Times packets
                e = BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
                chatTitle = BukkitReflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle, fadeIn, stay, fadeOut});
                BukkitReflection.sendPacket(titlePacket, players);

                e = BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object) null);
                chatTitle = BukkitReflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.getNMSClass("IChatBaseComponent")});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle});
                BukkitReflection.sendPacket(titlePacket, players);
            }

            if (subtitle != null)
            {
                // Times packets
                e = BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
                chatSubtitle = BukkitReflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
                BukkitReflection.sendPacket(subtitlePacket, players);

                e = BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object) null);
                chatSubtitle = BukkitReflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + subtitle.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
                BukkitReflection.sendPacket(subtitlePacket, players);
            }
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | InstantiationException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendActionBar(String text, Player... players)
    {
        try
        {
            if (text == null)
                text = "";

            final Object e = BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("ACTIONBAR").get((Object) null);
            final Object chatText = BukkitReflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
            final Constructor<?> subtitleConstructor = BukkitReflection.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.getNMSClass("IChatBaseComponent")});
            final Object titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatText});
            BukkitReflection.sendPacket(titlePacket, players);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    public static void clearActionBar(Player... players)
    {
        sendActionBar("", players);
    }

    public static void clearTitle(Player... players)
    {
        sendTitle(0, 0, 0, "", "", players);
    }

    public static void sendTabTitle(String header, String footer, Player... players)
    {
        if (header == null)
            header = "";
        if (footer == null)
            footer = "";

        try
        {
            final Object tabHeader = BukkitReflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
            final Object tabFooter = BukkitReflection.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
            final Constructor<?> titleConstructor = BukkitReflection.getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(BukkitReflection.getNMSClass("IChatBaseComponent"));
            final Object packet = titleConstructor.newInstance(tabHeader);
            final Field field = packet.getClass().getDeclaredField("b");
            field.setAccessible(true);
            field.set(packet, tabFooter);
            BukkitReflection.sendPacket(packet, players);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    public static int getPing(Player player)
    {
        int ping = 0;
        try
        {
            final Object craftPlayer = BukkitReflection.getHandle(player);
            ping = (int) BukkitReflection.getDeclaredField(craftPlayer.getClass(), "ping").get(craftPlayer);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return ping;
    }

    public static void sendMessage(CommandSender sender, String... messages)
    {
        for (String msg : messages)
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendMessage(String message, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static Stream<Player> streamInRange(Location center, double radius)
    {
        return center.getWorld().getNearbyEntities(center, radius, radius, radius).stream()
                .filter(e -> e instanceof Player)
                .map(e -> ((Player) e));
    }

    public static void forEachInRange(Location center, double radius, Consumer<Player> consumer)
    {
        streamInRange(center, radius)
                .forEach(consumer);
    }

}
