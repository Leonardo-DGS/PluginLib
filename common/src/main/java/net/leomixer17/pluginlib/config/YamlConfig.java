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

    public Object get(String path)
    {
        return null;
    }

    public void set(String path, Object value)
    {

    }

    public Collection<String> getKeys()
    {
        return getKeys(false);
    }

    public Collection<String> getKeys(boolean deep)
    {
        return null;
    }

    public YamlConfig getSection(String path)
    {
        return null;
    }

    public byte getByte(String path)
    {
        return 0;
    }

    public short getShort(String path)
    {
        return 0;
    }

    public int getInt(String path)
    {
        return 0;
    }

    public long getLong(String path)
    {
        return 0;
    }

    public float getFloat(String path)
    {
        return 0;
    }

    public double getDouble(String path)
    {
        return 0;
    }

    public boolean getBoolean(String path)
    {
        return false;
    }

    public char getChar(String path)
    {
        return '\0';
    }

    public String getString(String path)
    {
        return null;
    }

    public List<?> getList(String path)
    {
        return null;
    }
}
