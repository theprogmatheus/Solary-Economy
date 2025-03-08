package com.redeskyller.bukkit.solaryeconomy;

import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.database;
import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.tableName;

import java.math.BigDecimal;
import java.sql.ResultSet;

@Deprecated
public class Account {

	private final String name;
	private BigDecimal balance;
	private boolean toggle;

	public Account(final String nome)
	{
		this(nome, new BigDecimal(0.0D));
	}

	public Account(final String nome, final BigDecimal balance)
	{
		this(nome, balance, false);
	}

	public Account(final String nome, final BigDecimal balance, final boolean toggle)
	{
		this.name = nome;
		this.balance = balance;
		this.toggle = toggle;
	}

	public String getName()
	{
		return this.name;
	}

	public BigDecimal getBalance()
	{
		return this.balance;
	}

	public Account setBalance(BigDecimal balance)
	{
		this.balance = balance;
		return this;
	}

	public boolean isToggle()
	{
		return toggle;
	}

	public Account setToggle(boolean toggle)
	{
		this.toggle = toggle;
		return this;
	}

	public Account create()
	{
		try {
			database.query("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {
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
			database.query("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {

				if (resultSet.next())
					database.execute("DELETE FROM " + tableName + " WHERE name='" + this.name + "'");

			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this;
	}

	public Account save()
	{
		try {
			database.query("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {
				if (resultSet.next()) {

					database.execute("UPDATE " + tableName + " SET valor='" + this.balance.toPlainString()
							+ "', toggle='" + (this.toggle ? 1 : 0) + "' WHERE name='" + this.name + "'");
				} else {
					create();
				}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this;
	}

	public static Account valueOf(ResultSet result)
	{
		Account account = null;
		try {
			account = new Account(result.getString("name"));
			account.setBalance(new BigDecimal(result.getString("valor")));
			account.setToggle(result.getInt("toggle") == 1);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return account;
	}

}
