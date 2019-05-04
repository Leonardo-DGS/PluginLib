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

    public static void sendTitle(final Integer fadeIn, final Integer stay, final Integer fadeOut, final String title, final String subtitle, final Player... players)
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
                e = BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
                chatTitle = BukkitReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.nmsClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.nmsClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle, fadeIn, stay, fadeOut});
                BukkitReflection.sendPacket(titlePacket, players);

                e = BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object) null);
                chatTitle = BukkitReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.nmsClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.nmsClass("IChatBaseComponent")});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle});
                BukkitReflection.sendPacket(titlePacket, players);
            }

            if (subtitle != null)
            {
                // Times packets
                e = BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
                chatSubtitle = BukkitReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.nmsClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.nmsClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
                BukkitReflection.sendPacket(subtitlePacket, players);

                e = BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object) null);
                chatSubtitle = BukkitReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + subtitle.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
                subtitleConstructor = BukkitReflection.nmsClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.nmsClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
                BukkitReflection.sendPacket(subtitlePacket, players);
            }
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | InstantiationException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
    }

    public static void sendActionBar(String text, final Player... players)
    {
        try
        {
            if (text == null)
                text = "";

            final Object e = BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("ACTIONBAR").get((Object) null);
            final Object chatText = BukkitReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + text.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}"});
            final Constructor<?> subtitleConstructor = BukkitReflection.nmsClass("PacketPlayOutTitle").getConstructor(new Class[]{BukkitReflection.nmsClass("PacketPlayOutTitle").getDeclaredClasses()[0], BukkitReflection.nmsClass("IChatBaseComponent")});
            final Object titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatText});
            BukkitReflection.sendPacket(titlePacket, players);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }

    public static void clearActionBar(final Player... players)
    {
        sendActionBar("", players);
    }

    public static void clearTitle(final Player... players)
    {
        sendTitle(0, 0, 0, "", "", players);
    }

    public static void sendTabTitle(String header, String footer, final Player... players)
    {
        if (header == null)
            header = "";
        if (footer == null)
            footer = "";

        try
        {
            final Object tabHeader = BukkitReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
            final Object tabFooter = BukkitReflection.nmsClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
            final Constructor<?> titleConstructor = BukkitReflection.nmsClass("PacketPlayOutPlayerListHeaderFooter").getConstructor(BukkitReflection.nmsClass("IChatBaseComponent"));
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

    public static int getPing(final Player player)
    {
        int ping = 0;
        try
        {
            final Object craftPlayer = BukkitReflection.getHandle(player);
            ping = (int) BukkitReflection.getField(craftPlayer.getClass(), "ping").get(craftPlayer);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return ping;
    }

    public static void msg(CommandSender sender, String... msgs)
    {
        for (String msg : msgs)
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void msg(String msg, CommandSender... senders)
    {
        for (CommandSender sender : senders)
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
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
