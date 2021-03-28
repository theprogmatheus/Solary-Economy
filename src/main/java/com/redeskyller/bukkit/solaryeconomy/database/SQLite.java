package com.redeskyller.bukkit.solaryeconomy.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.plugin.Plugin;

public class SQLite implements Database {

	public SQLite(Plugin plugin) {
		this.plugin = plugin;
	}

	private Plugin plugin;
	private Connection connection;
	private Statement statement;

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public boolean open() {
		try {
			Class.forName("org.sqlite.JDBC");
			if (this.connection == null) {
				this.connection = DriverManager
						.getConnection("jdbc:sqlite:" + this.plugin.getDataFolder().getPath() + "/database.db");
			}
			if (this.statement == null && this.connection != null) {
				this.statement = this.connection.createStatement();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this.connection();
	}

	@Override
	public boolean connection() {
		return this.connection != null;
	}

	@Override
	public boolean close() {
		if (connection()) {
			try {
				if (this.statement != null)
					this.statement.close();
				if (this.connection != null)
					this.connection.close();

				this.statement = null;
				this.connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection();
	}

	@Override
	public String getHostname() {
		return "localhost";
	}

	@Override
	public String getDatabase() {
		return "database.db";
	}

	@Override
	public String getUsername() {
		return "root";
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getType() {
		return "SQLite";
	}

	@Override
	public int getPort() {
		return 3306;
	}

	@Override
	public ResultSet query(String query) {
		try {
			return this.statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean execute(String string) {
		try {
			return this.statement.execute(string);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Statement getStatement() {
		return this.statement;
	}

}
