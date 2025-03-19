package com.github.theprogmatheus.mc.solaryeconomy.database;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.j256.ormlite.support.ConnectionSource;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.logging.Logger;

@Getter
public abstract class DatabaseConnectionProvider {

    private final String type;
    private final String username;
    private final String password;
    private final String databaseUrl;
    private final String driverClassName;
    private final String driverDownloadLink;
    private final File driverFile;

    protected static final Logger log;
    private static final File driversFolder;

    static {
        log = SolaryEconomy.getInstance().getLogger();
        driversFolder = new File(SolaryEconomy.getInstance().getDataFolder(), "drivers");
        if (!driversFolder.exists())
            driversFolder.mkdirs();
    }


    public DatabaseConnectionProvider(String type, String databaseUrl, String username, String password, String driverClassName, String driverDownloadLink) {
        this.type = type;
        this.databaseUrl = databaseUrl;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.driverDownloadLink = driverDownloadLink;
        this.driverFile = new File(driversFolder, this.type.concat("-driver.jar").toLowerCase());
    }

    public abstract ConnectionSource connectionSource();

    public boolean loadDriver() {
        try {
            Class.forName(this.driverClassName);
            return true;
        } catch (ClassNotFoundException classNotFoundException) {
            if (isDriverLoaded()) return true;
            try {
                File driverFile = getDriverFile();

                if (!driverFile.exists())
                    driverFile = downloadDriverFile();

                URLClassLoader classLoader = new URLClassLoader(new URL[]{driverFile.toURI().toURL()}, DatabaseConnectionProvider.class.getClassLoader());
                Class<?> driverClass = Class.forName(this.driverClassName, true, classLoader);

                // Criar uma instância do driver e registrar manualmente no DriverManager
                Driver driverInstance = (Driver) driverClass.getDeclaredConstructor().newInstance();
                DriverManager.registerDriver(new DriverWrapper(driverInstance));
                return true;
            } catch (Exception e) {
                log.severe("Unable to load database driver " + this.type + ": " + e.getMessage());
                return false;
            }
        }
    }

    public boolean isDriverLoaded() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getName().equals(this.driverClassName))
                return true;
        }
        return false;
    }

    public File downloadDriverFile() throws IOException {

        log.info("Downloading " + this.type + " driver from: " + this.driverDownloadLink);

        HttpURLConnection connection = (HttpURLConnection) new URL(this.driverDownloadLink).openConnection();
        connection.setRequestMethod("HEAD");
        long totalSize = connection.getContentLengthLong();
        connection.disconnect();

        if (totalSize <= 0)
            throw new IOException("Failed to get file size.");

        log.info("Total Size: " + (totalSize / 1024) + " KB");

        InputStream inputStream = new URL(this.driverDownloadLink).openStream();
        ReadableByteChannel rbc = Channels.newChannel(inputStream);
        FileOutputStream fos = new FileOutputStream(getDriverFile());

        byte[] buffer = new byte[8192];
        int bytesRead;
        long downloaded = 0;
        long lastProgress = 0;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
            downloaded += bytesRead;

            int progress = (int) ((downloaded * 100) / totalSize);
            if (progress >= lastProgress + 5) {
                log.info("Driver download progress: " + progress + "%");
                lastProgress = progress;
            }
        }

        fos.close();
        inputStream.close();

        log.info("Driver download completed successfully");
        return getDriverFile();
    }

}
