package net.leomixer17.pluginlib.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YamlConfig {

    private static final char SEPARATOR = '.';
    final Map<String, Object> self = new LinkedHashMap<>();
    final Map<String, List<String>> comments = new LinkedHashMap<>();

    public YamlConfig()
    {

    }

    public boolean contains(String path)
    {
        return get(path) != null;
    }

    public Object get(final String path)
    {
        return null;
    }

    public void set(final String path, final Object value)
    {

    }

    public Collection<String> getKeys()
    {
        return getKeys(false);
    }

    public Collection<String> getKeys(final boolean deep)
    {
        return null;
    }

    public byte getByte(final String path)
    {
        return 0;
    }

    public short getShort(final String path)
    {
        return 0;
    }

    public int getInt(final String path)
    {
        return 0;
    }

    public long getLong(final String path)
    {
        return 0;
    }

    public float getFloat(final String path)
    {
        return 0;
    }

    public double getDouble(final String path)
    {
        return 0;
    }

    public boolean getBoolean(final String path)
    {
        return false;
    }

    public char getChar(final String path)
    {
        return '\0';
    }

    public String getString(final String path)
    {
        return null;
    }

    public List<?> getList(final String path)
    {
        return null;
    }
}
