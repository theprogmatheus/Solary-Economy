package com.github.theprogmatheus.mc.solaryeconomy.database;

import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.MariaDbDatabaseType;
import com.j256.ormlite.jdbc.db.MysqlDatabaseType;
import com.j256.ormlite.jdbc.db.PostgresDatabaseType;
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
            this.connectionSource = getConnectionSource();
            for (Class<?> entityClass : this.entities.keySet()) {
                this.entities.put(entityClass, setupDao(entityClass, this.connectionSource));
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

    private Dao<?, ?> setupDao(Class<?> entityClass, ConnectionSource connectionSource) throws SQLException {
        Dao<?, ?> entityDao = DaoManager.createDao(this.connectionSource, entityClass);

        // Create table if not exists

        try {
            entityDao.countOf();
        } catch (Exception e) {
            TableUtils.createTableIfNotExists(this.connectionSource, entityClass);
        }

        return entityDao;
    }

    public ConnectionSource getConnectionSource() throws SQLException {
        switch (Env.DATABASE_TYPE) {
            case "SQLite":
                return sqliteConnectionSource(Env.DATABASE_NAME);
            case "MySQL":
                return mysqlConnectionSource(Env.DATABASE_ADDRESS, Env.DATABASE_NAME, Env.DATABASE_USERNAME, Env.DATABASE_PASSWORD);
            case "MariaDB":
                return mariadbConnectionSource(Env.DATABASE_ADDRESS, Env.DATABASE_NAME, Env.DATABASE_USERNAME, Env.DATABASE_PASSWORD);
            case "PostgreSQL":
                return postgresqlConnectionSource(Env.DATABASE_ADDRESS, Env.DATABASE_NAME, Env.DATABASE_USERNAME, Env.DATABASE_PASSWORD);
            default:
                return sqliteConnectionSource("storage.sqlite");
        }
    }

    private ConnectionSource sqliteConnectionSource(String fileName) throws SQLException {
        if (!this.plugin.getDataFolder().exists())
            this.plugin.getDataFolder().mkdirs();
        File dbFile = new File(this.plugin.getDataFolder(), fileName.concat(".db"));
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

    private ConnectionSource postgresqlConnectionSource(String hostname, String dbName, String username, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found.", e);
        }
        String dbUrl = "jdbc:postgresql://" + hostname + "/" + dbName;
        return new JdbcConnectionSource(dbUrl, username, password, new PostgresDatabaseType());
    }
}
