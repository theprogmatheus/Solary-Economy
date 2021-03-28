package com.redeskyller.bukkit.solaryeconomy.plugin;

import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.database;
import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.tableName;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.plugin.objetos.Account;
import com.redeskyller.bukkit.solaryeconomy.plugin.vault.Vault;
import com.redeskyller.bukkit.solaryeconomy.util.Config;

public class Economia {

	private Map<String, Account> accounts;
	private List<Account> moneytop;
	private Account magnata;
	private Config config;

	public Economia()
	{
		this.config = SolaryEconomy.config;
		this.accounts = new HashMap<>();
	}

	public boolean createAccount(String name, BigDecimal valor)
	{
		if (!this.accounts.containsKey(name)) {
			Account account = new Account(name, valor);
			account.save();
			this.accounts.put(name, account);
			return true;
		}
		return false;
	}

	public boolean deleteAccount(String name)
	{
		if (this.accounts.containsKey(name)) {
			this.accounts.get(name).delete();
			this.accounts.remove(name);
			return true;
		}
		return false;
	}

	public BigDecimal getBalance(String name)
	{
		try {
			return this.accounts.get(name).getBalance();
		} catch (Exception exception) {
			return new BigDecimal(0.0);
		}
	}

	public boolean setBalance(String name, BigDecimal valor)
	{
		if (this.accounts.containsKey(name)) {
			this.accounts.get(name).setBalance(valor);
			this.accounts.get(name).saveAsync(20);
			return true;
		}
		return false;
	}

	public boolean addBalance(String name, BigDecimal valor)
	{
		return setBalance(name, getBalance(name).add(valor));
	}

	public boolean substractBalance(String name, BigDecimal valor)
	{
		return setBalance(name, getBalance(name).subtract(valor));
	}

	public boolean hasBalance(String name, BigDecimal balance)
	{
		try {
			return this.accounts.get(name).getBalance().doubleValue() >= balance.doubleValue();
		} catch (Exception exception) {
			return false;
		}
	}

	public boolean existsAccount(String name)
	{
		return this.accounts.containsKey(name);
	}

	public boolean toggle(String name)
	{
		if (this.accounts.containsKey(name)) {
			Account account = this.accounts.get(name);
			if (account.isToggle())
				account.setToggle(false);
			else
				account.setToggle(true);
			return account.isToggle();
		}
		return false;
	}

	public boolean isToggle(String name)
	{
		if (this.accounts.containsKey(name))
			return this.accounts.get(name).isToggle();
		return false;
	}

	public Map<String, Account> getAccounts()
	{
		return this.accounts;
	}

	public Account getMagnata()
	{
		return this.magnata;
	}

	public List<Account> getMoneyTop()
	{
		return this.moneytop;
	}

	public Economia load()
	{

		try {
			database.queryAsync("SELECT * FROM " + tableName, resultSet -> {
				while (resultSet.next())
					try {
						Account account = Account.valueOf(resultSet);
						if (account != null)
							this.accounts.put(account.getName(), account);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
			});
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		loadMoneyTop();
		return this;
	}

	public List<Account> loadMoneyTop()
	{
		this.moneytop = new ArrayList<>();

		String lastmagnata = "";
		if (this.magnata != null)
			lastmagnata = this.magnata.getName();

		this.magnata = null;

		try {

			ResultSet resultSet = database.query("SELECT * FROM "
					+ tableName.concat(" WHERE LENGTH(name) <= " + this.config.getYaml().getInt("economy_top.name_size")
							+ " ORDER BY CAST(valor AS DECIMAL) DESC LIMIT "
							+ SolaryEconomy.config.getYaml().getInt("economy_top.size") + ";"));

			while (resultSet.next())
				try {
					Account account = Account.valueOf(resultSet);
					if (account != null) {
						this.moneytop.add(account);
						if (this.magnata == null) {
							this.magnata = account;
							if ((!lastmagnata.equals(account.getName()))
									&& this.config.getYaml().getBoolean("magnata_broadcast")) {
								String accountname = account.getName();
								String valor = SolaryEconomy.numberFormat(account.getBalance());
								if (SolaryEconomy.config.getYaml().getBoolean("economy_top.prefix")) {
									Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
									if (vault != null)
										accountname = Vault.getPrefix(account.getName()).concat(account.getName());
								}
								for (World world : Bukkit.getWorlds())
									for (Player player : world.getPlayers()) {
										player.sendMessage(" ");
										player.sendMessage(SolaryEconomy.mensagens.get("MAGNATA_NEW")
												.replace("{player}", accountname).replace("{valor}", valor));
										player.sendMessage(" ");
									}
							}
						}
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return this.moneytop;
	}

}
