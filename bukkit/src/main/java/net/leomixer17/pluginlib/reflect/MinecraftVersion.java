package net.leomixer17.pluginlib.reflect;

import org.bukkit.Bukkit;

public enum MinecraftVersion {

    Unknown(0),
    v1_7_R4(174),
    v1_8_R3(183),
    v1_9_R1(191),
    v1_9_R2(192),
    v1_10_R1(1101),
    v1_11_R1(1111),
    v1_12_R1(1121),
    v1_13_R1(1131),
    v1_13_R2(1132),
    v1_14_R1(1141);

    private static MinecraftVersion version;
    private static Boolean hasGsonSupport;

    private final int versionId;

    MinecraftVersion(int versionId)
    {
        this.versionId = versionId;
    }

    public int getVersionId()
    {
        return this.versionId;
    }

    public static MinecraftVersion getVersion()
    {
        if (version == null)
            try
            {
                version = MinecraftVersion.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
            }
            catch (IllegalArgumentException e)
            {
                version = MinecraftVersion.Unknown;
            }
        return version;
    }

    public static boolean hasGsonSupport()
    {
        if (hasGsonSupport != null)
            return hasGsonSupport;
        try
        {
            Class.forName("com.google.gson.Gson");
            hasGsonSupport = true;
        }
        catch (ClassNotFoundException e)
        {
            hasGsonSupport = false;
        }
        return hasGsonSupport;
    }

}
