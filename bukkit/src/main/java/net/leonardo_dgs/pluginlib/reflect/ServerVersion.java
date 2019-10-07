package net.leonardo_dgs.pluginlib.reflect;

import org.bukkit.Bukkit;

import java.util.Comparator;

public final class ServerVersion implements Comparable<ServerVersion> {

    private static final ServerVersion CURRENT = parseServerVersion();

    private static final Comparator<ServerVersion> COMPARATOR =
            Comparator.nullsFirst(Comparator
                    .comparingInt(ServerVersion::getMajor)
                    .thenComparingInt(ServerVersion::getMinor)
                    .thenComparingInt(ServerVersion::getPatch)
            );

    private final int major;
    private final int minor;
    private final int patch;

    public static ServerVersion of(int major, int minor)
    {
        return of(major, minor, 0);
    }

    public static ServerVersion of(int major, int minor, int patch)
    {
        return new ServerVersion(major, minor, patch);
    }

    public static ServerVersion getCurrent()
    {
        return CURRENT;
    }

    public static ServerVersion parse(String version)
    {
        String[] parts = version.split("\\.");
        if (parts.length < 2)
            throw new IllegalArgumentException();
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = 0;
        if (parts.length == 2)
            patch = Integer.parseInt(parts[2]);
        return of(major, minor, patch);
    }

    private static ServerVersion parseServerVersion()
    {
        return parse(Bukkit.getBukkitVersion().split("-")[0]);
    }

    private ServerVersion(int major, int minor, int patch)
    {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    @Override
    public int compareTo(ServerVersion version)
    {
        return COMPARATOR.compare(this, version);
    }

    public boolean isAfter(ServerVersion other)
    {
        return compareTo(other) > 0;
    }

    public boolean isAfterOrEq(ServerVersion other)
    {
        return compareTo(other) >= 0;
    }

    public boolean isBefore(ServerVersion other)
    {
        return compareTo(other) < 0;
    }

    public boolean isBeforeOrEq(ServerVersion other)
    {
        return compareTo(other) <= 0;
    }

    public boolean isBetween(ServerVersion v1, ServerVersion v2)
    {
        return (isAfterOrEq(v1) && isBeforeOrEq(v2)) || (isBeforeOrEq(v1) && isAfterOrEq(v2));
    }

    public int getMajor()
    {
        return major;
    }

    public int getMinor()
    {
        return minor;
    }

    public int getPatch()
    {
        return patch;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (!(obj instanceof ServerVersion))
            return false;

        ServerVersion other = (ServerVersion) obj;
        return getMajor() == other.getMajor() &&
                getMinor() == other.getMinor() &&
                getPatch() == other.getPatch();
    }

    @Override
    public String toString()
    {
        return major + "." + minor + "." + patch;
    }

}
