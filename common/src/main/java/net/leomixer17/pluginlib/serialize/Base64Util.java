package net.leomixer17.pluginlib.serialize;

import java.util.Base64;

public final class Base64Util {

    public static String encode(byte[] buf)
    {
        return Base64.getEncoder().encodeToString(buf);
    }

    public static byte[] decode(String src)
    {
        try
        {
            return Base64.getDecoder().decode(src);
        }
        catch (IllegalArgumentException e)
        {
            throw e;
        }
    }

}
