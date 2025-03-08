package com.github.theprogmatheus.mc.solaryeconomy.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.MariaDbDatabaseType;
import com.j256.ormlite.jdbc.db.MysqlDatabaseType;
import com.j256.ormlite.jdbc.db.SqliteDatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class DatabaseManager {

    private final JavaPlugin plugin;
    private ConnectionSource connectionSource;
    private final Map<Class<?>, Dao<?, ?>> entities;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.entities = new HashMap<>();
    }


    public <M> void addEntityClass(Class<M> entityClass) {
        this.entities.put(entityClass, null);
    }

    public <M, I> Dao<M, I> getEntityDao(Class<M> entityClass, Class<I> idClass) {
        if (!this.entities.containsKey(entityClass)) return null;
        Dao<?, ?> dao = this.entities.get(entityClass);
        if (dao == null) return null;
        return (Dao<M, I>) this.entities.get(entityClass);
    }


    public void startDatabase() {
        try {


            // Alterar entre os tipos de banco de dados disponíveis.
            this.connectionSource = sqliteConnectionSource();

            if (!this.entities.isEmpty()) {
                for (Class<?> entityClass : this.entities.keySet()) {
                    this.entities.put(entityClass, DaoManager.createDao(this.connectionSource, entityClass));
                    TableUtils.createTableIfNotExists(this.connectionSource, entityClass);
                }
            }
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

    private ConnectionSource sqliteConnectionSource() throws SQLException {
        if (!this.plugin.getDataFolder().exists())
            this.plugin.getDataFolder().mkdirs();
        File dbFile = new File(this.plugin.getDataFolder(), "storage.sqlite.db");
        String dbUrl = "jdbc:sqlite:".concat(dbFile.getPath());
        return new JdbcConnectionSource(dbUrl, new SqliteDatabaseType());
    }

    private ConnectionSource mysqlConnectionSource(String hostname, String dbName, String username, String password) throws SQLException {
        String dbUrl = "jdbc:mysql://" + hostname + "/" + dbName;
        return new JdbcConnectionSource(dbUrl, username, password, new MysqlDatabaseType());
    }

    private ConnectionSource mariadbConnectionSource(String hostname, String dbName, String username, String password) throws SQLException {
        String dbUrl = "jdbc:mariadb://" + hostname + "/" + dbName;
        return new JdbcConnectionSource(dbUrl, username, password, new MariaDbDatabaseType());
    }
}
