package com.github.theprogmatheus.mc.solaryeconomy.database.providers;

import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseConnectionProvider;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.MariaDbDatabaseType;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class MariaDBConnectionProvider extends DatabaseConnectionProvider {

    public MariaDBConnectionProvider() {
        super(
                "MariaDB",
                "jdbc:mariadb://" + Env.DATABASE_ADDRESS + "/" + Env.DATABASE_NAME,
                Env.DATABASE_USERNAME,
                Env.DATABASE_PASSWORD,
                "org.mariadb.jdbc.Driver",
                Env.DATABASE_DRIVER_MARIADB
        );
    }

    @Override
    public ConnectionSource connectionSource() {
        if (this.loadDriver()) {
            try {
                return new JdbcConnectionSource(getDatabaseUrl(), getUsername(), getPassword(), new MariaDbDatabaseType());
            } catch (SQLException e) {
                log.severe("Unable to load db connection source " + getType() + ": " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
