package com.redeskyller.bukkit.solaryeconomy;

import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.database;
import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.tableName;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class Account {

	private final String name;

	public Account(final String nome)
	{
		this.name = nome;
	}

	public String getName()
	{
		return this.name;
	}

	public BigDecimal getBalance()
	{
		BigDecimal balance = new BigDecimal(0.0D);

		try (ResultSet resultSet = database
				.query("SELECT valor FROM " + tableName + " WHERE name='" + this.name + "'")) {

			if (resultSet.next())
				balance = new BigDecimal(resultSet.getString("valor"));

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return balance;
	}

	public Account setBalance(BigDecimal balance)
	{
		try {
			database.queryAsync("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {
				if (resultSet.next())
					database.execute("UPDATE " + tableName + " SET valor='" + balance.toPlainString() + "' WHERE name='"
							+ this.name + "'");
			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this;
	}

	public boolean isToggle()
	{
		boolean isToggle = false;

		try (ResultSet resultSet = database
				.query("SELECT toggle FROM " + tableName + " WHERE name='" + this.name + "'")) {

			if (resultSet.next())
				isToggle = (resultSet.getInt("toggle") >= 1 ? true : false);

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return isToggle;
	}

	public Account setToggle(boolean toggle)
	{
		try {
			database.queryAsync("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {
				if (resultSet.next())
					database.execute("UPDATE " + tableName + " SET toggle='" + (toggle ? 1 : 0) + "' WHERE name='"
							+ this.name + "'");
			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this;
	}

	public Account create(BigDecimal balance)
	{
		try {
			database.queryAsync("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {
				if (!resultSet.next())
					database.execute("INSERT INTO " + tableName + " VALUES ('" + this.name + "', '"
							+ balance.toPlainString() + "', '0')");
			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this;
	}

	public Account delete()
	{
		try {
			database.queryAsync("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {

				if (resultSet.next())
					database.execute("DELETE FROM " + tableName + " WHERE name='" + this.name + "'");

			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this;
	}

	public Account sync()
	{

		return this;
	}

	public static Account valueOf(ResultSet result)
	{
		Account account = null;
		try {
			account = new Account(result.getString("name"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return account;
	}

}
