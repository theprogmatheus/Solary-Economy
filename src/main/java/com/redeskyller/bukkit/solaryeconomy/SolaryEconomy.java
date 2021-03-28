package com.redeskyller.bukkit.solaryeconomy;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.redeskyller.bukkit.solaryeconomy.commands.SolaryCommand;
import com.redeskyller.bukkit.solaryeconomy.database.Database;
import com.redeskyller.bukkit.solaryeconomy.database.MySQL;
import com.redeskyller.bukkit.solaryeconomy.database.SQLite;
import com.redeskyller.bukkit.solaryeconomy.manager.Mensagens;
import com.redeskyller.bukkit.solaryeconomy.plugin.Economia;
import com.redeskyller.bukkit.solaryeconomy.plugin.objetos.Account;
import com.redeskyller.bukkit.solaryeconomy.plugin.objetos.RefreshMoneyTop;
import com.redeskyller.bukkit.solaryeconomy.plugin.vault.VaultEconomy;
import com.redeskyller.bukkit.solaryeconomy.util.Config;

public class SolaryEconomy extends JavaPlugin implements Listener {

	private static SolaryEconomy instance;

	public static Config config;
	public static Mensagens mensagens;

	public static Database database;
	public static String tableName;

	public static Economia economia;
	public static RefreshMoneyTop refreshMoneyTop;

	@Override
	public void onEnable()
	{
		config = new Config(this, "config.yml");

		setupDatabase();

		mensagens = new Mensagens(this);
		economia = new Economia().load();
		refreshMoneyTop = new RefreshMoneyTop();

		if (config.getYaml().getBoolean("use_vault"))
			try {
				Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
				if (vault != null)
					new VaultEconomy();
				Plugin legendchat = Bukkit.getPluginManager().getPlugin("Legendchat");
				if (legendchat != null) {
					Class<?> listener_clazz = Class
							.forName("com.redeskyller.bukkit.solaryeconomy.plugin.LegendChatListeners");
					Object listener = listener_clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
					Bukkit.getServer().getPluginManager().registerEvents((Listener) listener, this);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}

		getServer().getPluginManager().registerEvents(this, this);

		getCommand("money").setExecutor(new SolaryCommand("money"));
	}

	@Override
	public void onDisable()
	{
		database.endConnection();
	}

	private void setupDatabase()
	{
		try {

			FileConfiguration config = getConfig();

			if (config.getBoolean("mysql.enable")) {
				tableName = config.getString("mysql.table");
				database = new MySQL(this);
			} else {
				tableName = ("solaryeconomy");
				database = new SQLite(this);
			}

			database.execute(
					"CREATE TABLE IF NOT EXISTS " + tableName + " (name varchar(40), valor text, toggle int);");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static String numberFormat(BigDecimal bigDecimal)
	{
		String formated = "";
		double doubleValue = bigDecimal.doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.GERMAN));
		formated += decimalFormat.format(bigDecimal);

		if ((doubleValue >= -1) && (doubleValue <= 1))
			formated += " " + config.getString("currency_name.singular");
		else
			formated += " " + config.getString("currency_name.plural");

		return formated;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		if (!economia.existsAccount(event.getPlayer().getName()))
			economia.createAccount(event.getPlayer().getName(),
					new BigDecimal(config.getYaml().getDouble("start_value")));
	}

	public static SolaryEconomy getInstance()
	{
		return instance;
	}

	public static Account getMagnata()
	{
		return economia.getMagnata();
	}

	public static boolean isToggle(String account)
	{
		return economia.isToggle(account);
	}

	public static List<Account> getMoneyTop()
	{
		return economia.getMoneyTop();
	}

}
