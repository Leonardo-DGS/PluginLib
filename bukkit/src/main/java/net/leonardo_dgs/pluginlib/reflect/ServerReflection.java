package net.leonardo_dgs.pluginlib.reflect;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ServerReflection {

    public static Class<?> nmsClass(String nms)
    {
        try
        {
            return Class.forName("net.minecraft.server." + NmsVersion.getCurrent().toString() + "." + nms);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> obcClass(String obc)
    {
        try
        {
            return Class.forName("org.bukkit.craftbukkit." + NmsVersion.getCurrent().toString() + "." + obc);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Object getHandle(Object obj)
    {
        try
        {
            return getDeclaredMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String name)
    {
        try
        {
            return clazz.getField(name);
        }
        catch (NoSuchFieldException | SecurityException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getDeclaredField(Class<?> clazz, String name)
    {
        try
        {
            final Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException | SecurityException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> boolean setField(Class<T> from, Object obj, String field, Object newValue)
    {
        try
        {
            final Field f = from.getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, newValue);
            return true;
        }
        catch (Exception e)
        {
        }
        return false;
    }

    public static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... args)
    {
        try
        {
            final Method method = clazz.getDeclaredMethod(name, args);
            method.setAccessible(true);
            return method;
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>... args)
    {
        try
        {
            return clazz.getMethod(name, args);
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getConnection(Player player)
    {
        try
        {
            final Object craftPlayer = getHandle(player);
            return getField(craftPlayer.getClass(), "playerConnection").get(craftPlayer);
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendPacket(Object packet, Player... players)
    {
        try
        {
            for (final Player player : players)
            {
                final Object connection = getConnection(player);
                getDeclaredMethod(connection.getClass(), "sendPacket", ServerReflection.nmsClass("Packet")).invoke(connection, packet);
            }
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Object nmsBlock(Block block)
    {
        try
        {
            final Method method = getDeclaredMethod(obcClass("util.CraftMagicNumbers"), "getBlock", Block.class);
            return method.invoke(obcClass("util.CraftMagicNumbers"), block);
        }
        catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}