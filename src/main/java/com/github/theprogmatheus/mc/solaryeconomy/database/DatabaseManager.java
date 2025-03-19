package com.github.theprogmatheus.mc.solaryeconomy.database;

import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.providers.MariaDBConnectionProvider;
import com.github.theprogmatheus.mc.solaryeconomy.database.providers.MySQLConnectionProvider;
import com.github.theprogmatheus.mc.solaryeconomy.database.providers.PostgreSQLConnectionProvider;
import com.github.theprogmatheus.mc.solaryeconomy.database.providers.SQLiteConnectionProvider;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class DatabaseManager {

    private final JavaPlugin plugin;
    private DatabaseConnectionProvider connectionProvider;
    private ConnectionSource connectionSource;
    private final Map<Class<?>, Dao<?, ?>> entities;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.entities = new HashMap<>();
    }


    public <M> void addEntityClass(Class<M> entityClass) {
        this.entities.put(entityClass, null);
        this.setNewTableName(entityClass);
    }

    public <M, I> Dao<M, I> getEntityDao(Class<M> entityClass, Class<I> idClass) {
        if (!this.entities.containsKey(entityClass)) return null;
        Dao<?, ?> dao = this.entities.get(entityClass);
        if (dao == null) return null;
        return (Dao<M, I>) this.entities.get(entityClass);
    }


    public void startDatabase() {
        try {
            this.connectionProvider = loadConnectionProvider();
            this.connectionSource = this.connectionProvider.connectionSource();
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
            this.connectionSource = null;
        } catch (Exception e) {
            throw new RuntimeException("Unable to shutdown database.", e);
        }
    }

    private void setNewTableName(Class<?> entityClass) {
        DatabaseTable annotation = entityClass.getAnnotation(DatabaseTable.class);
        if (annotation != null) {
            try {
                Field field = Proxy.getInvocationHandler(annotation).getClass().getDeclaredField("memberValues");
                field.setAccessible(true);
                Map<String, Object> memberValues = (Map<String, Object>) field.get(Proxy.getInvocationHandler(annotation));
                String currentName = (String) memberValues.get("tableName");
                memberValues.put("tableName", (Env.DATABASE_TABLE_PREFIX != null ? Env.DATABASE_TABLE_PREFIX : "").concat(currentName));
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    private DatabaseConnectionProvider loadConnectionProvider() {
        switch (Env.DATABASE_TYPE.toLowerCase()) {
            case "mysql":
                return new MySQLConnectionProvider();
            case "mariadb":
                return new MariaDBConnectionProvider();
            case "postgresql":
                return new PostgreSQLConnectionProvider();
            default:
                return new SQLiteConnectionProvider();
        }
    }

}
