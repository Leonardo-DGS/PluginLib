package net.leomixer17.pluginlib.reflect;

import org.bukkit.Bukkit;

public enum MinecraftVersion {

    Unknown,
    v1_8_R3,
    v1_9_R1,
    v1_9_R2,
    v1_10_R1,
    v1_11_R1,
    v1_12_R1,
    v1_13_R1,
    v1_13_R2,
    v1_14_R1;

    private static MinecraftVersion version;

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

}
