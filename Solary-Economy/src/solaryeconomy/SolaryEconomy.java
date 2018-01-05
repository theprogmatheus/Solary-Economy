package solaryeconomy;

import java.text.NumberFormat;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import solaryeconomy.commands.SolaryCommand;
import solaryeconomy.database.Database;
import solaryeconomy.database.MySQL;
import solaryeconomy.database.SQLite;
import solaryeconomy.manager.Mensagens;
import solaryeconomy.plugin.Economia;
import solaryeconomy.plugin.objetos.RefreshMoneyTop;
import solaryeconomy.plugin.vault.VaultEconomy;
import solaryeconomy.util.Config;

public class SolaryEconomy extends JavaPlugin implements Listener {

	public static final String PLUGIN_NAME = "Solary-Economy";
	public static final String AUTHOR = "Sr_Edition";
	public static final String VERSION = "1.1";

	public static String table;

	public static SolaryEconomy instance;
	public static Database database;
	public static Mensagens mensagens;
	public static Economia economia;
	public static RefreshMoneyTop refreshMoneyTop;
	public static Config config;
	public static Thread converting;

	// Método quando o plugin é ativado
	@Override
	public void onEnable() {
		instance = this;
		config = new Config(instance, "config.yml");
		database();
		new SolaryCommand(PLUGIN_NAME, "money", "coin", "coins", "solaryeconomy").register();
		mensagens = new Mensagens(instance);
		economia = new Economia();
		refreshMoneyTop = new RefreshMoneyTop();
		getServer().getPluginManager().registerEvents(this, this);
		if (config.getYaml().getBoolean("use_vault")) {
			try {
				Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
				if (vault != null) {
					new VaultEconomy();
				}
				Plugin legendchat = Bukkit.getPluginManager().getPlugin("Legendchat");
				if (legendchat != null) {
					Class<?> listener_clazz = Class.forName("solaryeconomy.plugin.listener.LegendChatListeners");
					Object listener = listener_clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
					Bukkit.getServer().getPluginManager().registerEvents((Listener) listener, instance);
				}else {
					System.out.println("NULL!");
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

	}

	// Método quando o plugin é desativado
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		if (database != null) {
			if (database.connection()) {
				database.close();
			}
		}
		if (converting != null) {
			Bukkit.getConsoleSender().sendMessage("§a[Solary-Economy] §eOcorreu um erro na conversao de contas.");
			converting.stop();
		}

	}

	public static String getServerVersion() {
		return Bukkit.getServer().getVersion().split("MC: ")[1].replaceAll("\\)", "").trim();
	}

	public void database() {
		try {
			echo("iniciando banco de dados...");
			FileConfiguration config = getConfig();
			boolean usemysql = config.getBoolean("mysql.enable");
			if (usemysql) {
				echo("tipo do banco de dados \"MySQL\" selecionado.");
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
				echo("tipo do banco de dados \"SQLite\" selecionado.");
				table = PLUGIN_NAME.toLowerCase().replace("-", "");
				;
				database = new SQLite(instance);
			}

			echo("testando conexao com banco de dados...");
			if (database.open()) {
				echo("conexao testada com sucesso, tudo OK!");
				database.close();
			} else {
				echo("houve um erro ao conectar-se com o banco de dados!");
				echo("tipo do banco de dados \"SQLite\" selecionado.");
				table = PLUGIN_NAME.toLowerCase().replace("-", "");
				database = new SQLite(instance);
			}
			database.open();

			database.execute("create table if not exists " + table + " (name varchar(40), valor double, toggle int);");

			database.close();

		} catch (Exception e) {
			e.printStackTrace();
			echo("houve um erro ao tentar iniciar o banco de dados.");
		}
	}

	public static String numberFormat(double valor) {
		NumberFormat nb = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));
		nb.setMaximumFractionDigits(0);
		String formated = nb.format(valor);
		if (valor > 1) {
			formated += " " + config.getString("currency_name.plural");
		} else {
			formated += " " + config.getString("currency_name.singular");
		}

		return formated;
	}

	public static void echo(String message) {
		System.out.println("[" + PLUGIN_NAME + "] " + message);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (!economia.hasAccount(event.getPlayer().getName())) {
			economia.createAccount(event.getPlayer().getName(), config.getYaml().getDouble("start_value"));
		}
	}

}
