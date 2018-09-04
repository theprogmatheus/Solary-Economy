package nuvemplugins.solaryeconomy.plugin;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import nuvemplugins.solaryeconomy.app.SolaryEconomy;
import nuvemplugins.solaryeconomy.database.Database;
import nuvemplugins.solaryeconomy.plugin.objetos.Account;
import nuvemplugins.solaryeconomy.plugin.vault.Vault;
import nuvemplugins.solaryeconomy.util.Config;

public class Economia {

	private Map<String, Account> accounts;
	private List<Account> moneytop;
	private Account magnata;
	private Config config;

	public Economia() {
		this.config = SolaryEconomy.config;
		this.accounts = new HashMap<>();
		this.load();
	}

	public boolean createAccount(String name, double valor) {
		if (!this.accounts.containsKey(name)) {
			Account account = new Account(name, valor);
			account.save();
			this.accounts.put(name, account);
			return true;
		}
		return false;
	}

	public boolean deleteAccount(String name) {
		if (this.accounts.containsKey(name)) {
			this.accounts.get(name).delete();
			this.accounts.remove(name);
			return true;
		}
		return false;
	}

	public boolean setMoney(String name, double valor) {
		if (this.accounts.containsKey(name)) {
			this.accounts.get(name).setValor(valor);
			this.accounts.get(name).saveAsync(100);
			return true;
		}
		return false;
	}

	public double getMoney(String name) {
		if (this.accounts.containsKey(name))
			return this.accounts.get(name).getValor();
		return 0;
	}

	public boolean hasAccount(String name) {
		return this.accounts.containsKey(name);
	}

	public boolean toggle(String name) {
		if (this.accounts.containsKey(name)) {
			Account account = this.accounts.get(name);
			if (account.isToggle()) {
				account.setToggle(false);
			} else {
				account.setToggle(true);
			}
			return account.isToggle();
		}
		return false;
	}

	public boolean isToggle(String name) {
		if (this.accounts.containsKey(name))
			return this.accounts.get(name).isToggle();
		return false;
	}

	public boolean addMoney(String name, double valor) {
		return this.setMoney(name, this.getMoney(name) + valor);
	}

	public boolean takeMoney(String name, double valor) {
		return this.setMoney(name, ((this.getMoney(name) - valor) < 0 ? 0.0 : this.getMoney(name) - valor));
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public Account getMagnata() {
		return magnata;
	}

	public List<Account> getMoneyTop() {
		return this.moneytop;
	}

	public void load() {
		Database database = SolaryEconomy.database;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table);
			while (result.next()) {
				try {
					Account account = Account.valueOf(result);
					if (account != null) {
						this.accounts.put(account.getName(), account);
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();

		this.loadMoneyTop();
	}

	public List<Account> loadMoneyTop() {
		Database database = SolaryEconomy.database;
		this.moneytop = new ArrayList<>();

		String lastmagnata = "";
		if (this.magnata != null)
			lastmagnata = this.magnata.getName();

		this.magnata = null;

		database.open();
		try {
			

			ResultSet result = database.query("select * from " + SolaryEconomy.table.concat(" where length(name) <= "
					+ this.config.getYaml().getInt("economy_top.name_size") + " order by cast(valor as decimal) desc limit "
					+ SolaryEconomy.config.getYaml().getInt("economy_top.size") + ";"));

			while (result.next()) {
				try {
					Account account = Account.valueOf(result);
					if (account != null) {
						this.moneytop.add(account);
						if (this.magnata == null) {
							this.magnata = account;
							if ((!lastmagnata.equals(account.getName()))
									&& this.config.getYaml().getBoolean("magnata_broadcast")) {
								String accountname = account.getName();
								String valor = SolaryEconomy.numberFormat(account.getValor());
								if (SolaryEconomy.config.getYaml().getBoolean("economy_top.prefix")) {
									Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
									if (vault != null) {
										accountname = Vault.getPrefix(account.getName()).concat(account.getName());
									}
								}
								for (World world : Bukkit.getWorlds()) {
									for (Player player : world.getPlayers()) {
										player.sendMessage(" ");
										player.sendMessage(SolaryEconomy.mensagens.get("MAGNATA_NEW")
												.replace("{player}", accountname).replace("{valor}", valor));
										player.sendMessage(" ");
									}
								}
							}
						}
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();

		return this.moneytop;
	}

}
