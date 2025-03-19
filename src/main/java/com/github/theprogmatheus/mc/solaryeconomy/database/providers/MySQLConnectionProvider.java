package com.github.theprogmatheus.mc.solaryeconomy.database.providers;

import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseConnectionProvider;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.MysqlDatabaseType;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class MySQLConnectionProvider extends DatabaseConnectionProvider {

    public MySQLConnectionProvider() {
        super(
                "MySQL",
                "jdbc:mysql://" + Env.DATABASE_ADDRESS + "/" + Env.DATABASE_NAME,
                Env.DATABASE_USERNAME,
                Env.DATABASE_PASSWORD,
                "com.mysql.cj.jdbc.Driver",
                Env.DATABASE_DRIVER_MYSQL
        );
    }

    @Override
    public ConnectionSource connectionSource() {
        if (this.loadDriver()) {
            try {
                return new JdbcConnectionSource(getDatabaseUrl(), getUsername(), getPassword(), new MysqlDatabaseType());
            } catch (SQLException e) {
                log.severe("Unable to load db connection source " + getType() + ": " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
