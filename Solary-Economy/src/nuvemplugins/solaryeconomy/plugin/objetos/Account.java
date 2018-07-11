package nuvemplugins.solaryeconomy.plugin.objetos;

import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import nuvemplugins.solaryeconomy.app.SolaryEconomy;
import nuvemplugins.solaryeconomy.database.Database;

public class Account {

	public Account(String nome, double valor) {
		this.name = nome;
		this.valor = valor;
	}

	private String name;
	private double valor;
	private boolean toggle;

	private BukkitTask asyncSaveTask;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public boolean isToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	public void saveAsync(long delay) {
		if (this.asyncSaveTask == null) {
			this.asyncSaveTask = Bukkit.getScheduler().runTaskLaterAsynchronously(SolaryEconomy.instance,
					new Runnable() {
						@Override
						public void run() {
							try {
								Account.this.save();
								Account.this.asyncSaveTask = null;
							} catch (Exception exception) {
								exception.printStackTrace();
							}
						}
					}, delay);
		}
	}

	public void save() {
		Database database = SolaryEconomy.database;
		String table = SolaryEconomy.table;
		database.open();
		try {
			ResultSet result = database.query("select toggle from " + table + " where name='" + this.name + "'");
			if (result.next()) {
				database.execute("update " + table + " set valor='" + this.valor + "', toggle='" + (this.toggle ? 1 : 0)
						+ "' where name='" + this.name + "'");
			} else {
				database.execute("insert into " + table + " values ('" + this.name + "', '" + this.valor + "', '"
						+ (this.toggle ? 1 : 0) + "')");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
	}

	public void delete() {
		Database database = SolaryEconomy.database;
		String table = SolaryEconomy.table;
		database.open();
		try {
			ResultSet result = database.query("select toggle from " + table + " where name='" + this.name + "'");
			if (result.next()) {
				database.execute("delete from " + table + " where name='" + this.name + "'");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		database.close();
	}

	public static Account valueOf(ResultSet result) {
		Account account = null;
		try {
			String name = result.getString("name");
			double valor = result.getDouble("valor");
			boolean toggle = result.getInt("toggle") >= 1 ? true : false;
			account = new Account(name, valor);
			account.setToggle(toggle);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return account;
	}

}
