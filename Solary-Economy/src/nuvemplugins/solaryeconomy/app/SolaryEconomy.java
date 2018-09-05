package nuvemplugins.solaryeconomy.app;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import nuvemplugins.solaryeconomy.database.Database;
import nuvemplugins.solaryeconomy.database.MySQL;
import nuvemplugins.solaryeconomy.database.SQLite;
import nuvemplugins.solaryeconomy.manager.Mensagens;
import nuvemplugins.solaryeconomy.plugin.Economia;
import nuvemplugins.solaryeconomy.plugin.objetos.RefreshMoneyTop;
import nuvemplugins.solaryeconomy.plugin.vault.VaultEconomy;
import nuvemplugins.solaryeconomy.util.Config;

public class SolaryEconomy implements Listener {

	public SolaryEconomy(JavaPlugin plugin) {
		instance = plugin;
	}

	public static final String PLUGIN_NAME = "Solary-Economy";
	public static final String AUTHOR = "Sr_Edition";
	public static final String VERSION = "1.4";

	public static String table;

	public static JavaPlugin instance;
	public static Database database;
	public static Mensagens mensagens;
	public static Economia economia;
	public static RefreshMoneyTop refreshMoneyTop;
	public static Config config;

	public void onEnable() {
		config = new Config(instance, "config.yml");
		database();
		mensagens = new Mensagens(instance);
		economia = new Economia();
		refreshMoneyTop = new RefreshMoneyTop();
		instance.getServer().getPluginManager().registerEvents(this, instance);
		if (config.getYaml().getBoolean("use_vault")) {
			try {
				Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
				if (vault != null) {
					new VaultEconomy();
				}
				Plugin legendchat = Bukkit.getPluginManager().getPlugin("Legendchat");
				if (legendchat != null) {
					Class<?> listener_clazz = Class
							.forName("nuvemplugins.solaryeconomy.plugin.listener.LegendChatListeners");
					Object listener = listener_clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
					Bukkit.getServer().getPluginManager().registerEvents((Listener) listener, instance);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

	}

	public void onDisable() {
		if (database != null) {
			if (database.connection()) {
				database.close();
			}
		}

	}

	public void database() {
		try {
			FileConfiguration config = instance.getConfig();
			boolean usemysql = config.getBoolean("mysql.enable");
			if (usemysql) {
				String hostname = config.getString("mysql.hostname");
				String database_name = config.getString("mysql.database");
				String username = config.getString("mysql.username");
				String password = config.getString("mysql.password");
				String table_name = config.getString("mysql.table");
				int port = config.getInt("mysql.port");
				MySQL mysql = new MySQL(instance);
				mysql.setHostname(hostname);
				mysql.setDatabase(database_name);
				mysql.setUsername(username);
				mysql.setPassword(password);
				mysql.setPort(port);
				table = table_name;
				database = mysql;

			} else {
				table = PLUGIN_NAME.toLowerCase().replace("-", "");
				database = new SQLite(instance);
			}

			if (database.open()) {
				database.close();
			} else {
				table = PLUGIN_NAME.toLowerCase().replace("-", "");
				database = new SQLite(instance);
			}
			database.open();

			database.execute("create table if not exists " + table + " (name varchar(40), valor text, toggle int);");

			database.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String numberFormat(BigDecimal bigDecimal) {
		DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.GERMAN));
		String formated = decimalFormat.format(bigDecimal);
		if (bigDecimal.doubleValue() > 1) {
			formated += " " + config.getString("currency_name.plural");
		} else {
			formated += " " + config.getString("currency_name.singular");
		}

		return formated;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (!economia.existsAccount(event.getPlayer().getName())) {
			economia.createAccount(event.getPlayer().getName(),
					new BigDecimal(config.getYaml().getDouble("start_value")));
		}
	}

}
