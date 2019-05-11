package net.leomixer17.pluginlib.nbt;

import net.leomixer17.pluginlib.reflect.BukkitReflection;
import net.leomixer17.pluginlib.reflect.MethodNames;
import net.leomixer17.pluginlib.reflect.MinecraftVersion;
import net.leomixer17.pluginlib.util.GsonWrapper;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;

public class NBTReflectionUtil {

    private static Class<?> getCraftItemStack()
    {
        return BukkitReflection.getOBCClass("inventory.CraftItemStack");
    }

    private static Class<?> getCraftEntity()
    {
        return BukkitReflection.getOBCClass("entity.CraftEntity");
    }

    protected static Class<?> getNBTBase()
    {
        return BukkitReflection.getNMSClass("NBTBase");
    }

    protected static Class<?> getNBTTagString()
    {
        return BukkitReflection.getNMSClass("NBTTagString");
    }

    protected static Class<?> getNMSItemStack()
    {
        return BukkitReflection.getNMSClass("ItemStack");
    }

    protected static Class<?> getNBTTagCompound()
    {
        return BukkitReflection.getNMSClass("NBTTagCompound");
    }

    protected static Class<?> getNBTCompressedStreamTools()
    {
        return BukkitReflection.getNMSClass("NBTCompressedStreamTools");
    }

    protected static Class<?> getMojangsonParser()
    {
        return BukkitReflection.getNMSClass("MojangsonParser");
    }

    protected static Class<?> getTileEntity()
    {
        return BukkitReflection.getNMSClass("TileEntity");
    }

    protected static Class<?> getCraftWorld()
    {
        return BukkitReflection.getOBCClass("CraftWorld");
    }

    public static Object getNewNBTTag()
    {
        return BukkitReflection.newFromNMS("NBTTagCompound");
    }

    private static Object getNewBlockPosition(int x, int y, int z)
    {
        try
        {
            Class<?> clazz = BukkitReflection.getNMSClass("BlockPosition");
            return clazz.getConstructor(int.class, int.class, int.class).newInstance(x, y, z);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Object setNBTTag(Object NBTTag, Object NMSItem)
    {
        try
        {
            Method method;
            method = NMSItem.getClass().getMethod("setTag", NBTTag.getClass());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getNMSItemStack(ItemStack item)
    {
        Class<?> clazz = getCraftItemStack();
        Method method;
        try
        {
            method = clazz.getMethod("asNMSCopy", ItemStack.class);
            Object answer = method.invoke(clazz, item);
            return answer;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getNMSEntity(Entity entity)
    {
        Class<?> clazz = getCraftEntity();
        Method method;
        try
        {
            method = clazz.getMethod("getHandle");
            return method.invoke(getCraftEntity().cast(entity));
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object parseNBT(String json)
    {
        Class<?> cis = getMojangsonParser();
        Method method;
        try
        {
            method = cis.getMethod("parse", String.class);
            return method.invoke(null, json);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object readNBTFile(FileInputStream stream)
    {
        Class<?> clazz = getNBTCompressedStreamTools();
        Method method;
        try
        {
            method = clazz.getMethod("a", InputStream.class);
            return method.invoke(clazz, stream);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object saveNBTFile(Object nbt, FileOutputStream stream)
    {
        Class<?> clazz = getNBTCompressedStreamTools();
        Method method;
        try
        {
            method = clazz.getMethod("a", getNBTTagCompound(), OutputStream.class);
            return method.invoke(clazz, nbt, stream);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack getBukkitItemStack(Object item)
    {
        Class<?> clazz = getCraftItemStack();
        Method method;
        try
        {
            method = clazz.getMethod("asCraftMirror", item.getClass());
            Object answer = method.invoke(clazz, item);
            return (ItemStack) answer;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getItemRootNBTTagCompound(Object nmsitem)
    {
        Class<? extends Object> clazz = nmsitem.getClass();
        Method method;
        try
        {
            method = clazz.getMethod("getTag");
            Object answer = method.invoke(nmsitem);
            return answer;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object convertNBTCompoundtoNMSItem(NBTCompound nbtcompound)
    {
        Class<?> clazz = getNMSItemStack();
        try
        {
            Object nmsstack = clazz.getConstructor(getNBTTagCompound()).newInstance(gettoCompount(nbtcompound.getCompound(), nbtcompound));
            return nmsstack;
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static NBTContainer convertNMSItemtoNBTCompound(Object nmsitem)
    {
        Class<? extends Object> clazz = nmsitem.getClass();
        Method method;
        try
        {
            method = clazz.getMethod("save", getNBTTagCompound());
            Object answer = method.invoke(nmsitem, getNewNBTTag());
            return new NBTContainer(answer);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getEntityNBTTagCompound(Object nmsitem)
    {
        Class<? extends Object> c = nmsitem.getClass();
        Method method;
        try
        {
            method = c.getMethod(MethodNames.getEntityNbtGetterMethodName(), getNBTTagCompound());
            Object nbt = getNBTTagCompound().newInstance();
            Object answer = method.invoke(nmsitem, nbt);
            if (answer == null)
                answer = nbt;
            return answer;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Object setEntityNBTTag(Object NBTTag, Object NMSItem)
    {
        try
        {
            Method method;
            method = NMSItem.getClass().getMethod(MethodNames.getEntityNbtSetterMethodName(), getNBTTagCompound());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static Object getTileEntityNBTTagCompound(BlockState tile)
    {
        Method method;
        try
        {
            Object pos = getNewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = getCraftWorld().cast(tile.getWorld());
            Object nmsworld = cworld.getClass().getMethod("getHandle").invoke(cworld);
            Object o = nmsworld.getClass().getMethod("getTileEntity", pos.getClass()).invoke(nmsworld, pos);
            method = getTileEntity().getMethod(MethodNames.getTileDataMethodName(), getNBTTagCompound());
            Object tag = getNBTTagCompound().newInstance();
            Object answer = method.invoke(o, tag);
            if (answer == null)
                answer = tag;
            return answer;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setTileEntityNBTTagCompound(BlockState tile, Object comp)
    {
        Method method;
        try
        {
            Object pos = getNewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
            Object cworld = getCraftWorld().cast(tile.getWorld());
            Object nmsworld = cworld.getClass().getMethod("getHandle").invoke(cworld);
            Object o = nmsworld.getClass().getMethod("getTileEntity", pos.getClass()).invoke(nmsworld, pos);
            method = getTileEntity().getMethod("a", getNBTTagCompound());
            method.invoke(o, comp);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Object getSubNBTTagCompound(Object compound, String name)
    {
        Class<? extends Object> c = compound.getClass();
        Method method;
        try
        {
            method = c.getMethod("getCompound", String.class);
            Object answer = method.invoke(compound, name);
            return answer;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void addNBTTagCompound(NBTCompound comp, String name)
    {
        if (name == null)
        {
            remove(comp, name);
            return;
        }
        Object nbttag = comp.getCompound();
        if (nbttag == null)
        {
            nbttag = getNewNBTTag();
        }
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(nbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("set", String.class, getNBTBase());
            method.invoke(workingtag, name, getNBTTagCompound().newInstance());
            comp.setCompound(nbttag);
            return;
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e)
        {
            e.printStackTrace();
        }
        return;
    }

    public static Boolean valideCompound(NBTCompound comp)
    {
        Object root = comp.getCompound();
        if (root == null)
            root = getNewNBTTag();
        return (gettoCompount(root, comp)) != null;
    }

    public static Object gettoCompount(Object nbttag, NBTCompound comp)
    {
        Stack<String> structure = new Stack<>();
        while (comp.getParent() != null)
        {
            structure.add(comp.getName());
            comp = comp.getParent();
        }
        while (!structure.isEmpty())
        {
            nbttag = getSubNBTTagCompound(nbttag, structure.pop());
            if (nbttag == null)
                return null;
        }
        return nbttag;
    }

    public static void addOtherNBTCompound(NBTCompound comp, NBTCompound nbtcompound)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("a", getNBTTagCompound());
            method.invoke(workingtag, nbtcompound.getCompound());
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static void setString(NBTCompound comp, String key, String text)
    {
        if (text == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(workingtag, key, text);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static String getString(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getString", String.class);
            return (String) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContent(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("get", String.class);
            return method.invoke(workingtag, key).toString();
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setInt(NBTCompound comp, String key, Integer i)
    {
        if (i == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setInt", String.class, int.class);
            method.invoke(workingtag, key, i);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Integer getInt(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getInt", String.class);
            return (Integer) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setByteArray(NBTCompound comp, String key, byte[] b)
    {
        if (b == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setByteArray", String.class, byte[].class);
            method.invoke(workingtag, key, b);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return;
    }

    public static byte[] getByteArray(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getByteArray", String.class);
            return (byte[]) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setIntArray(NBTCompound comp, String key, int[] i)
    {
        if (i == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setIntArray", String.class, int[].class);
            method.invoke(workingtag, key, i);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static int[] getIntArray(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getIntArray", String.class);
            return (int[]) method.invoke(workingtag, key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFloat(NBTCompound comp, String key, Float f)
    {
        if (f == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setFloat", String.class, float.class);
            method.invoke(workingtag, key, (float) f);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Float getFloat(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getFloat", String.class);
            return (Float) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setLong(NBTCompound comp, String key, Long f)
    {
        if (f == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setLong", String.class, long.class);
            method.invoke(workingtag, key, (long) f);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Long getLong(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getLong", String.class);
            return (Long) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setShort(NBTCompound comp, String key, Short f)
    {
        if (f == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setShort", String.class, short.class);
            method.invoke(workingtag, key, (short) f);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Short getShort(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getShort", String.class);
            return (Short) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setByte(NBTCompound comp, String key, Byte f)
    {
        if (f == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setByte", String.class, byte.class);
            method.invoke(workingtag, key, (byte) f);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Byte getByte(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getByte", String.class);
            return (Byte) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setDouble(NBTCompound comp, String key, Double d)
    {
        if (d == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setDouble", String.class, double.class);
            method.invoke(workingtag, key, d);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Double getDouble(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getDouble", String.class);
            return (Double) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte getType(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return 0;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod(MethodNames.getTypeMethodName(), String.class);
            return (byte) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setBoolean(NBTCompound comp, String key, Boolean d)
    {
        if (d == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("setBoolean", String.class, boolean.class);
            method.invoke(workingtag, key, d);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Boolean getBoolean(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getBoolean", String.class);
            return (Boolean) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void set(NBTCompound comp, String key, Object val)
    {
        if (val == null)
        {
            remove(comp, key);
            return;
        }
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp))
        {
            new Throwable("InvalideCompound").printStackTrace();
            return;
        }
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("set", String.class, getNBTBase());
            method.invoke(workingtag, key, val);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static NBTList getList(NBTCompound comp, String key, NBTType type)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("getList", String.class, int.class);
            return new NBTList(comp, key, type, method.invoke(workingtag, key, type.getId()));
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void setObject(NBTCompound comp, String key, Object value)
    {
        if (!MinecraftVersion.hasGsonSupport()) return;
        String json = GsonWrapper.getString(value);
        setString(comp, key, json);
    }

    public static <T> T getObject(NBTCompound comp, String key, Class<T> type)
    {
        if (!MinecraftVersion.hasGsonSupport()) return null;
        String json = getString(comp, key);
        if (json == null)
            return null;
        return GsonWrapper.deserializeJson(json, type);
    }

    public static void remove(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("remove", String.class);
            method.invoke(workingtag, key);
            comp.setCompound(rootnbttag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }

    public static Boolean hasKey(NBTCompound comp, String key)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("hasKey", String.class);
            return (Boolean) method.invoke(workingtag, key);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Set<String> getKeys(NBTCompound comp)
    {
        Object rootnbttag = comp.getCompound();
        if (rootnbttag == null)
            rootnbttag = getNewNBTTag();
        if (!valideCompound(comp)) return null;
        Object workingtag = gettoCompount(rootnbttag, comp);
        Method method;
        try
        {
            method = workingtag.getClass().getMethod("c");
            return (Set<String>) method.invoke(workingtag);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
