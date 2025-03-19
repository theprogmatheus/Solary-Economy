package com.github.theprogmatheus.mc.solaryeconomy.database.providers;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseConnectionProvider;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.db.SqliteDatabaseType;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.sql.SQLException;

public class SQLiteConnectionProvider extends DatabaseConnectionProvider {

    private static final File dbFile;

    static {
        File folder = SolaryEconomy.getInstance().getDataFolder();
        if (!folder.exists())
            folder.mkdirs();

        dbFile = new File(folder, Env.DATABASE_NAME.concat(".db"));
    }

    public SQLiteConnectionProvider() {
        super(
                "SQLite",
                "jdbc:sqlite:" + dbFile.getPath(),
                "",
                "",
                "org.sqlite.JDBC",
                Env.DATABASE_DRIVER_SQLITE
        );
    }

    @Override
    public ConnectionSource connectionSource() {
        if (this.loadDriver()) {
            try {
                return new JdbcConnectionSource(getDatabaseUrl(), getUsername(), getPassword(), new SqliteDatabaseType());
            } catch (SQLException e) {
                log.severe("Unable to load db connection source " + getType() + ": " + e.getMessage());
                return null;
            }
        }
        return null;
    }
}
