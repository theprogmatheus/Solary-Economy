package com.redeskyller.bukkit.solaryeconomy.plugin.objetos;

import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.database;
import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.tableName;

import java.math.BigDecimal;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

public class Account {

	public Account(String nome, BigDecimal valor)
	{
		this.name = nome;
		this.balance = valor;
	}

	private String name;
	private BigDecimal balance;
	private boolean toggle;

	private BukkitTask asyncSaveTask;

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public BigDecimal getBalance()
	{
		return this.balance;
	}

	public void setBalance(BigDecimal valor)
	{
		this.balance = valor;
	}

	public boolean isToggle()
	{
		return this.toggle;
	}

	public void setToggle(boolean toggle)
	{
		this.toggle = toggle;
	}

	public void saveAsync(long delay)
	{
		if (this.asyncSaveTask == null)
			this.asyncSaveTask = Bukkit.getScheduler().runTaskLater(SolaryEconomy.getInstance(), new Runnable() {
				@Override
				public void run()
				{
					try {
						Account.this.save();
						Account.this.asyncSaveTask = null;
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}, delay);
	}

	public void save()
	{
		try {
			database.queryAsync("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {
				if (resultSet.next())
					database.execute("UPDATE " + tableName + " SET valor='" + this.balance.toPlainString()
							+ "', toggle='" + (this.toggle ? 1 : 0) + "' WHERE name='" + this.name + "'");
				else
					database.execute("INSERT INTO " + tableName + " VALUES ('" + this.name + "', '"
							+ this.balance.toPlainString() + "', '" + (this.toggle ? 1 : 0) + "')");
			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void delete()
	{
		try {
			database.queryAsync("SELECT 1 FROM " + tableName + " WHERE name='" + this.name + "'", resultSet -> {

				if (resultSet.next())
					database.execute("DELETE FROM " + tableName + " WHERE name='" + this.name + "'");

			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static Account valueOf(ResultSet result)
	{
		Account account = null;
		try {
			String name = result.getString("name");
			String valorString = result.getString("valor");
			boolean toggle = result.getInt("toggle") >= 1 ? true : false;
			account = new Account(name, new BigDecimal(valorString));
			account.setToggle(toggle);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return account;
	}

}
