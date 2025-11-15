package com.redeskyller.bukkit.solaryeconomy.bootstrap;

import com.redeskyller.bukkit.solaryeconomy.database.SqlQueryLoader;
import com.redeskyller.bukkit.solaryeconomy.database.repository.AccountRepository;
import com.redeskyller.bukkit.solaryeconomy.database.repository.EconomyRepository;
import com.redeskyller.bukkit.solaryeconomy.database.repository.impl.AccountRepositoryImpl;
import com.redeskyller.bukkit.solaryeconomy.database.repository.impl.EconomyRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import static com.redeskyller.bukkit.solaryeconomy.config.env.Config.*;

@RequiredArgsConstructor
@Getter
public class DatabaseBootstrap {

    private final JavaPlugin plugin;

    private HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;
    private SqlQueryLoader sqlQueryLoader;
    private EconomyRepository economyRepository;
    private AccountRepository accountRepository;

    public void initializeDatabase() {
        this.hikariConfig = loadHikariConfig();
        this.hikariDataSource = new HikariDataSource(this.hikariConfig);
        this.sqlQueryLoader = loadQueryLoader();
        this.economyRepository = new EconomyRepositoryImpl(this.hikariDataSource, this.sqlQueryLoader, this.plugin.getLogger());
        this.accountRepository = new AccountRepositoryImpl(this.hikariDataSource, this.sqlQueryLoader, this.plugin.getLogger());
        this.economyRepository.createTableIfNotExists();
        this.accountRepository.createTableIfNotExists();
    }

    public void shutdownDatabase() {
        this.hikariDataSource.close();
    }

    private HikariConfig loadHikariConfig() {
        String dbType = DATABASE_TYPE.getValue().toLowerCase();
        switch (dbType) {
            case "mysql":
                return getMySQLHikariConfig();
            case "mariadb":
                return getMariaDBHikariConfig();
            case "sqlite":
            default:
                return getSQLiteHikariConfig();
        }
    }

    private SqlQueryLoader loadQueryLoader() {
        return new SqlQueryLoader(
                DATABASE_TYPE.getValue().toLowerCase().replace("mariadb", "mysql"),
                DATABASE_MYSQL_TABLE_PREFIX.getValue(),
                new ConcurrentHashMap<>()
        );
    }

    private HikariConfig getMySQLHikariConfig() {
        HikariConfig config = new HikariConfig();

        String preferredDriver = "com.mysql.cj.jdbc.Driver";
        String fallbackDriver = "com.mysql.jdbc.Driver";

        try {
            Class.forName(preferredDriver);
            config.setDriverClassName(preferredDriver);
        } catch (ClassNotFoundException e) {
            config.setDriverClassName(fallbackDriver);
        }

        config.setJdbcUrl("jdbc:mysql://" +
                DATABASE_MYSQL_HOST.getValue() +
                ":" + DATABASE_MYSQL_PORT.getValue() +
                "/" + DATABASE_MYSQL_DATABASE.getValue() +
                "?useSSL=false" +
                "&autoReconnect=true" +
                "&characterEncoding=UTF-8" +
                "&allowPublicKeyRetrieval=true" +
                "&useUnicode=true" +
                "&serverTimezone=UTC");

        config.setUsername(DATABASE_MYSQL_USERNAME.getValue());
        config.setPassword(DATABASE_MYSQL_PASSWORD.getValue());

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(1800000);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "256");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");

        return config;
    }

    private HikariConfig getMariaDBHikariConfig() {
        HikariConfig config = new HikariConfig();

        String mariadbDriver = "org.mariadb.jdbc.Driver";
        String mysqlFallbackDriver = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(mariadbDriver);
            config.setDriverClassName(mariadbDriver);
        } catch (ClassNotFoundException e) {
            config.setDriverClassName(mysqlFallbackDriver);
        }

        config.setJdbcUrl("jdbc:mariadb://" +
                DATABASE_MYSQL_HOST.getValue() +
                ":" + DATABASE_MYSQL_PORT.getValue() +
                "/" + DATABASE_MYSQL_DATABASE.getValue() +
                "?useUnicode=true" +
                "&characterEncoding=UTF-8" +
                "&serverTimezone=UTC");

        config.setUsername(DATABASE_MYSQL_USERNAME.getValue());
        config.setPassword(DATABASE_MYSQL_PASSWORD.getValue());

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(1800000);

        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "256");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        return config;
    }

    private HikariConfig getSQLiteHikariConfig() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:" + new File(this.plugin.getDataFolder(), "database.sqlite.db").getAbsolutePath());

        config.setMaximumPoolSize(1);
        config.setMinimumIdle(1);
        config.setConnectionTimeout(10000);
        config.setIdleTimeout(0);
        config.setMaxLifetime(0);

        config.addDataSourceProperty("journal_mode", "WAL");
        config.addDataSourceProperty("busy_timeout", "5000");
        config.addDataSourceProperty("synchronous", "NORMAL");

        return config;
    }
}
