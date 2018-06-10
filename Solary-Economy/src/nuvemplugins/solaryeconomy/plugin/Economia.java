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
import nuvemplugins.solaryeconomy.util.Cache;
import nuvemplugins.solaryeconomy.util.Config;

public class Economia {

	private Map<String, Cache> caches;
	private List<Account> moneytop;
	private Account magnata;
	private Database database;
	private Config config;

	public Economia() {
		this.caches = new HashMap<>();
		this.database = SolaryEconomy.database;
		this.config = SolaryEconomy.config;
		loadMoneyTop();
	}

	public Account getMagnata() {
		return magnata;
	}

	public boolean createAccount(String name, double valor) {
		boolean created = false;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table + " where name='" + name + "';");
			if (!result.next()) {
				database.execute(
						"insert into " + SolaryEconomy.table + " values ('" + name + "', '" + valor + "', '0');");
				created = true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();

		return created;
	}

	public boolean deleteAccount(String name) {
		boolean deleted = false;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table + " where name='" + name + "';");
			if (result.next()) {
				database.execute("delete from " + SolaryEconomy.table + " where name='" + name + "';");
				deleted = true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
		return deleted;
	}

	public boolean setMoney(String name, double valor) {
		boolean set = false;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table + " where name='" + name + "';");
			if (result.next()) {
				database.execute(
						"update " + SolaryEconomy.table + " set valor='" + valor + "' where name='" + name + "';");
				set = true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
		return set;
	}

	public double getMoney(String name) {
		Cache cache = this.caches.get(name);
		if (cache != null) {
			if (cache.isCanRefresh()) {
				cache.setMoney(this.getMoneyBySQL(name));
			}
		} else {
			cache = new Cache(name, this.getMoneyBySQL(name), 1);
			this.caches.put(name, cache);
		}
		return cache.getMoney();
	}

	private double getMoneyBySQL(String name) {
		double valor = 0.0;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table + " where name='" + name + "';");
			if (result.next()) {
				valor = result.getDouble("valor");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
		return valor;
	}

	public boolean hasAccount(String name) {
		boolean has = false;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table + " where name='" + name + "';");
			has = result.next();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
		return has;
	}

	public boolean toggle(String name) {
		boolean toggle = false;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table + " where name='" + name + "';");
			if (result.next()) {
				int value = result.getInt("toggle");
				if (value == 1) {
					database.execute("update " + SolaryEconomy.table + " set toggle='0' where name='" + name + "';");
					toggle = false;
				} else if (value == 0) {
					database.execute("update " + SolaryEconomy.table + " set toggle='1' where name='" + name + "';");
					toggle = true;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
		return toggle;
	}

	public boolean isToggle(String name) {
		boolean toggle = false;
		database.open();
		try {
			ResultSet result = database.query("select * from " + SolaryEconomy.table + " where name='" + name + "';");
			if (result.next()) {
				toggle = result.getInt("toggle") == 1 ? true : false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
		return toggle;
	}

	public boolean addMoney(String name, double valor) {
		return setMoney(name, (this.getMoneyBySQL(name) + valor));
	}

	public boolean takeMoney(String name, double valor) {
		return setMoney(name, (this.getMoneyBySQL(name) - valor));
	}

	public List<Account> loadMoneyTop() {
		if (this.moneytop == null)
			this.moneytop = new ArrayList<>();
		String lastmagnata = "";
		if (this.magnata != null)
			lastmagnata = this.magnata.getName();

		this.magnata = null;

		this.moneytop.clear();
		int namesize = this.config.getYaml().getInt("economy_top.name_size");
		int max = SolaryEconomy.config.getYaml().getInt("economy_top.size");
		int i = 0;
		try {
			database.open();
			ResultSet result = database.query("select * from " + SolaryEconomy.table.concat(" order by valor desc;"));
			while (result.next()) {
				String name = result.getString("name");
				if (name.length() <= namesize) {
					if (i < max) {
						Account account = new Account(name, result.getDouble("valor"));

						if (this.magnata == null) {
							this.magnata = account;

							if (!lastmagnata.equals(account.getName())) {

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

						this.moneytop.add(account);
						i++;
					}
				}
			}

			database.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return this.moneytop;
	}

	public List<Account> getMoneyTop() {
		return this.moneytop;
	}
}
