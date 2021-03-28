package com.redeskyller.bukkit.solaryeconomy;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.redeskyller.bukkit.solaryeconomy.commands.SolaryCommand;
import com.redeskyller.bukkit.solaryeconomy.database.Database;
import com.redeskyller.bukkit.solaryeconomy.database.MySQL;
import com.redeskyller.bukkit.solaryeconomy.database.SQLite;
import com.redeskyller.bukkit.solaryeconomy.hook.VaultEconomy;
import com.redeskyller.bukkit.solaryeconomy.listeners.EconomyPlayerListener;
import com.redeskyller.bukkit.solaryeconomy.runnables.RefreshMoneyTop;
import com.redeskyller.bukkit.solaryeconomy.util.Configuration;
import com.redeskyller.bukkit.solaryeconomy.util.Messages;

public class SolaryEconomy extends JavaPlugin {

	private static SolaryEconomy instance;

	public static Configuration config;
	public static Messages messages;

	public static Database database;
	public static String tableName;

	public static Economia economia;
	public static RefreshMoneyTop refreshMoneyTop;

	public static VaultEconomy vaultEconomy;

	@Override
	public void onEnable()
	{
		instance = this;
		
		config = new Configuration(this, new File(getDataFolder(), "config.yml")).load();

		setupDatabase();

		messages = new Messages(this).load();
		economia = new Economia().load();
		refreshMoneyTop = new RefreshMoneyTop().load();

		if (config.getBoolean("use_vault"))
			setupVault();

		getServer().getPluginManager().registerEvents(new EconomyPlayerListener(), getInstance());

		getCommand("money").setExecutor(new SolaryCommand("money"));
	}

	@Override
	public void onDisable()
	{
		database.endConnection();
		if (vaultEconomy != null)
			vaultEconomy.unregister();
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

	private void setupVault()
	{
		try {
			Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");

			if (vault != null)
				vaultEconomy = new VaultEconomy().register();

			Plugin legendchat = Bukkit.getPluginManager().getPlugin("Legendchat");
			if (legendchat != null) {
				Class<?> listener_clazz = Class
						.forName("com.redeskyller.bukkit.solaryeconomy.hook.LegendChatListeners");
				Object listener = listener_clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
				Bukkit.getServer().getPluginManager().registerEvents((Listener) listener, this);
			}

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

	public static SolaryEconomy getInstance()
	{
		return instance;
	}

	public static RankAccount getMagnata()
	{
		return economia.getMagnata();
	}

	public static boolean isToggle(String account)
	{
		return economia.isToggle(account);
	}

	public static List<RankAccount> getMoneyTop()
	{
		return economia.getMoneyTop();
	}

}
