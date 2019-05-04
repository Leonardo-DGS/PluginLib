package net.leomixer17.pluginlib.util;

import com.google.gson.Gson;

public final class GsonWrapper {

    private static final Gson gson = new Gson();

    public static String getString(Object obj)
    {
        return gson.toJson(obj);
    }

    public static <T> T deserializeJson(String json, final Class<T> type)
    {
        if (json == null)
            return null;

        final T obj = gson.fromJson(json, type);
        return type.cast(obj);
    }

}
