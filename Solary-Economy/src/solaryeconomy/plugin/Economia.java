package solaryeconomy.plugin;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import solaryeconomy.SolaryEconomy;
import solaryeconomy.database.Database;
import solaryeconomy.plugin.objetos.Account;
import solaryeconomy.util.Config;

public class Economia {

	private List<Account> moneytop;
	private Database database;
	private Config config;

	public Economia() {
		this.database = SolaryEconomy.database;
		this.config = SolaryEconomy.config;
		loadMoneyTop();
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
		double valor = 0;
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
		return setMoney(name, (getMoney(name) + valor));
	}

	public boolean takeMoney(String name, double valor) {
		return setMoney(name, (getMoney(name) - valor));
	}

	public List<Account> loadMoneyTop() {
		if (this.moneytop == null)
			this.moneytop = new ArrayList<>();

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
						this.moneytop.add(new Account(name, result.getDouble("valor")));
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
