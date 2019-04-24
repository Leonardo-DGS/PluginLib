package net.leomixer17.pluginlib.database;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLDatabaseLibraryUtils {

    public static void downloadDatabaseLibrary(final String libraryUrl, final File jarFile)
    {
        try
        {
            downloadDatabaseLibrary(new URL(libraryUrl), jarFile);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public static void downloadDatabaseLibrary(final URL libraryUrl, final File jarFile)
    {
        if (!jarFile.exists())
            try
            {
                jarFile.getParentFile().mkdirs();
                final InputStream is = libraryUrl.openStream();
                Files.copy(is, jarFile.toPath());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
    }

    public static void registerDatabaseDriver(final String driverClassName, final File jarFile)
    {
        try
        {
            final URL u = new URL("jar:file:" + jarFile.getAbsolutePath() + "!/");
            final URLClassLoader ucl = new URLClassLoader(new URL[]{u});
            final Driver driver = (Driver) Class.forName(driverClassName, true, ucl).newInstance();
            DriverManager.registerDriver(new DriverShim(driver));
        }
        catch (MalformedURLException | SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | SecurityException e)
        {
            e.printStackTrace();
        }
    }

    public static void registerDatabaseDriver(final String driverClassName, final String libraryUrl, final File jarFile)
    {
        try
        {
            registerDatabaseDriver(driverClassName, new URL(libraryUrl), jarFile);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public static void registerDatabaseDriver(final String driverClassName, final URL libraryUrl, final File jarFile)
    {
        downloadDatabaseLibrary(libraryUrl, jarFile);
        registerDatabaseDriver(driverClassName, jarFile);
    }

}
