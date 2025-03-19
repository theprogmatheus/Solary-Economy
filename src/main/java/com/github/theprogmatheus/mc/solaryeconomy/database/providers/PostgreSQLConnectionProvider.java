package com.github.theprogmatheus.mc.solaryeconomy.database.providers;

import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseConnectionProvider;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.PostgresDatabaseType;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class PostgreSQLConnectionProvider extends DatabaseConnectionProvider {

    public PostgreSQLConnectionProvider() {
        super(
                "PostgreSQL",
                "jdbc:postgresql://" + Env.DATABASE_ADDRESS + "/" + Env.DATABASE_NAME,
                Env.DATABASE_USERNAME,
                Env.DATABASE_PASSWORD,
                "org.postgresql.Driver",
                Env.DATABASE_DRIVER_POSTGRESQL
        );
    }

    @Override
    public ConnectionSource connectionSource() {
        if (this.loadDriver()) {
            try {
                return new JdbcConnectionSource(getDatabaseUrl(), getUsername(), getPassword(), new PostgresDatabaseType());
            } catch (SQLException e) {
                log.severe("Unable to load db connection source " + getType() + ": " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
