package net.leomixer17.pluginlib.reflect;

import org.bukkit.Bukkit;

public enum MinecraftVersion {

    Unknown(Integer.MAX_VALUE),
    v1_7_R4(174),
    v1_8_R1(181),
    v1_8_R2(182),
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

    public int getId()
    {
        return versionId;
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
        if (hasGsonSupport == null)
            try
            {
                Class.forName("com.google.gson.Gson");
                hasGsonSupport = true;
            }
            catch (ClassNotFoundException ex)
            {
                hasGsonSupport = false;
            }
        return hasGsonSupport;
    }

}
