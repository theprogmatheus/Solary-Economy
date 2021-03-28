package com.redeskyller.bukkit.solaryeconomy;

import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.config;
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

import com.redeskyller.bukkit.solaryeconomy.hook.VaultChat;

public class Economia {

	private Map<String, Account> accounts;
	
	private List<RankAccount> moneytop;
	private RankAccount magnata;

	public Economia()
	{
		this.accounts = new HashMap<>();
	}

	public boolean createAccount(String name, BigDecimal valor)
	{
		if (!existsAccount(name)) {
			this.accounts.put(name, new Account(name).create(valor));
			return true;
		}
		return false;
	}

	public boolean deleteAccount(String name)
	{
		if (existsAccount(name)) {
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
		if (existsAccount(name)) {
			this.accounts.get(name).setBalance(valor);
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

	public boolean toggle(String name)
	{
		if (existsAccount(name)) {
			Account account = this.accounts.get(name);

			account.setToggle(!account.isToggle());

			return account.isToggle();
		}
		return false;
	}

	public boolean existsAccount(String name)
	{
		return this.accounts.containsKey(name);
	}

	public boolean isToggle(String name)
	{
		if (existsAccount(name))
			return this.accounts.get(name).isToggle();
		return false;
	}

	public Map<String, Account> getAccounts()
	{
		return this.accounts;
	}

	public RankAccount getMagnata()
	{
		return this.magnata;
	}

	public List<RankAccount> getMoneyTop()
	{
		return this.moneytop;
	}

	public Economia load()
	{

		try {
			ResultSet resultSet = database.query("SELECT name FROM " + tableName);

			while (resultSet.next())
				try {
					Account account = Account.valueOf(resultSet);
					if (account != null)
						this.accounts.put(account.getName(), account);
				} catch (Exception exception) {
					exception.printStackTrace();
				}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		loadMoneyTop();
		return this;
	}

	public List<RankAccount> loadMoneyTop()
	{
		this.moneytop = new ArrayList<>();

		String lastmagnata = "";
		if (this.magnata != null)
			lastmagnata = this.magnata.getName();

		this.magnata = null;

		try {

			ResultSet resultSet = database.query("SELECT * FROM " + tableName.concat(" WHERE LENGTH(name) <= "
					+ config.getInt("economy_top.name_size") + " ORDER BY CAST(valor AS DECIMAL) DESC LIMIT "
					+ config.getInt("economy_top.size") + ";"));

			while (resultSet.next())
				try {

					RankAccount rankAccount = new RankAccount(resultSet.getString("name"),
							new BigDecimal(resultSet.getString("valor")));

					this.moneytop.add(rankAccount);
					if (this.magnata == null) {
						this.magnata = rankAccount;
						if ((!lastmagnata.equals(rankAccount.getName())) && config.getBoolean("magnata_broadcast")) {
							String accountname = rankAccount.getName();
							String valor = SolaryEconomy.numberFormat(rankAccount.getBalance());
							if (config.getBoolean("economy_top.prefix")) {
								Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
								if (vault != null)
									accountname = VaultChat.getPrefix(rankAccount.getName())
											.concat(rankAccount.getName());
							}
							for (World world : Bukkit.getWorlds())
								for (Player player : world.getPlayers()) {
									player.sendMessage(" ");
									player.sendMessage(SolaryEconomy.messages.get("MAGNATA_NEW")
											.replace("{player}", accountname).replace("{valor}", valor));
									player.sendMessage(" ");
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
