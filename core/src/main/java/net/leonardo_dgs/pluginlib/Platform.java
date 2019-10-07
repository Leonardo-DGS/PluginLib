package net.leonardo_dgs.pluginlib;

public enum Platform {

    BUNGEE, SPIGOT;

    private static Platform current;

    public static Platform getCurrent()
    {
        if (current == null)
        {

        }
        return current;
    }

}
