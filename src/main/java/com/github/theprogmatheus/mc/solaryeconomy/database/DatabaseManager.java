package com.github.theprogmatheus.mc.solaryeconomy.database;

import com.github.theprogmatheus.mc.solaryeconomy.model.Counter;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

@Getter
public class DatabaseManager {

    private final JavaPlugin plugin;
    private ConnectionSource connectionSource;
    private Dao<Counter, String> countersDao;


    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public void setupDatabase() {
        try {
            if (!this.plugin.getDataFolder().exists())
                this.plugin.getDataFolder().mkdirs();

            File dbFile = new File(this.plugin.getDataFolder(), "ormlite.storage.db");
            String dbUrl = "jdbc:sqlite:".concat(dbFile.getPath());
            this.connectionSource = new JdbcConnectionSource(dbUrl);
            this.countersDao = DaoManager.createDao(this.connectionSource, Counter.class);

            TableUtils.createTableIfNotExists(this.connectionSource, Counter.class);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to setup database.", e);
        }
    }

    public void shutdownDatabase() {
        if (this.connectionSource == null) return;
        try {
            this.connectionSource.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to shutdown database.", e);
        }
    }
}
