package net.leonardo_dgs.pluginlib.reflect;

import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum NmsVersion {

    v1_8_R1(
            ServerVersion.of(1, 8, 0)
    ),
    v1_8_R2(
            ServerVersion.of(1, 8, 3)
    ),
    v1_8_R3(
            ServerVersion.of(1, 8, 8)
    ),
    v1_9_R1(
            ServerVersion.of(1, 9, 0),
            ServerVersion.of(1, 9, 2)
    ),
    v1_9_R2(
            ServerVersion.of(1, 9, 4)
    ),
    v1_10_R1(
            ServerVersion.of(1, 10, 2)
    ),
    v1_11_R1(
            ServerVersion.of(1, 11, 0),
            ServerVersion.of(1, 11, 2)
    ),
    v1_12_R1(
            ServerVersion.of(1, 12, 0),
            ServerVersion.of(1, 12, 1),
            ServerVersion.of(1, 12, 2)
    ),
    v1_13_R1(
            ServerVersion.of(1, 13, 0)
    ),
    v1_13_R2(
            ServerVersion.of(1, 13, 1),
            ServerVersion.of(1, 13, 2)
    ),
    v1_14_R1(
            ServerVersion.of(1, 14, 0),
            ServerVersion.of(1, 14, 1),
            ServerVersion.of(1, 14, 2),
            ServerVersion.of(1, 14, 3),
            ServerVersion.of(1, 14, 4)
    ),
    UNKNOWN;

    private final Set<ServerVersion> minecraftVersions;

    private static final NmsVersion CURRENT = forServerVersion(ServerVersion.getCurrent());
    private static final Map<ServerVersion, NmsVersion> MC_TO_NMS = new HashMap<>();

    static
    {
        for (NmsVersion nmsVersion : values())
            nmsVersion.getMinecraftVersions().forEach(minecraftVersion -> MC_TO_NMS.put(minecraftVersion, nmsVersion));
    }

    NmsVersion(ServerVersion... minecraftVersions)
    {
        this.minecraftVersions = ImmutableSet.copyOf(minecraftVersions);
    }

    public Set<ServerVersion> getMinecraftVersions()
    {
        return minecraftVersions;
    }

    public boolean isAfter(NmsVersion other)
    {
        return compareTo(other) > 0;
    }

    public boolean isAfterOrEq(NmsVersion other)
    {
        return compareTo(other) >= 0;
    }

    public boolean isBefore(NmsVersion other)
    {
        return compareTo(other) < 0;
    }

    public boolean isBeforeOrEq(NmsVersion other)
    {
        return compareTo(other) <= 0;
    }

    public boolean isBetween(NmsVersion v1, NmsVersion v2)
    {
        return (isAfterOrEq(v1) && isBeforeOrEq(v2)) || (isBeforeOrEq(v1) && isAfterOrEq(v2));
    }

    public static NmsVersion getCurrent()
    {
        return CURRENT;
    }

    public static NmsVersion forServerVersion(ServerVersion minecraftVersion)
    {
        return MC_TO_NMS.get(minecraftVersion);
    }

}
