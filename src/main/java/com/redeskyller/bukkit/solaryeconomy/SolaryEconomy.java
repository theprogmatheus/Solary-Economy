package com.redeskyller.bukkit.solaryeconomy;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.redeskyller.bukkit.solaryeconomy.commands.SolaryCommand;
import com.redeskyller.bukkit.solaryeconomy.database.Database;
import com.redeskyller.bukkit.solaryeconomy.database.MySQL;
import com.redeskyller.bukkit.solaryeconomy.database.SQLite;
import com.redeskyller.bukkit.solaryeconomy.hook.PlaceholdersHook;
import com.redeskyller.bukkit.solaryeconomy.hook.VaultEconomy;
import com.redeskyller.bukkit.solaryeconomy.listeners.EconomyPlayerListener;
import com.redeskyller.bukkit.solaryeconomy.runnables.RefreshMoneyTop;
import com.redeskyller.bukkit.solaryeconomy.util.Configuration;
import com.redeskyller.bukkit.solaryeconomy.util.CurrencyFormatter;
import com.redeskyller.bukkit.solaryeconomy.util.Messages;

@Deprecated
public class SolaryEconomy extends JavaPlugin {

	private static SolaryEconomy instance;

	public static Configuration config;
	public static Messages messages;

	public static Database database;
	public static String tableName;

	public static Economia economia;
	public static RefreshMoneyTop refreshMoneyTop;

	public static VaultEconomy vaultEconomy;

	public static CurrencyFormatter currencyFormatter;

	@Override
	public void onEnable() {
		instance = this;

		config = new Configuration(this, new File(getDataFolder(), "config.yml")).load();

		setupDatabase();

		messages = new Messages(this).load();
		economia = new Economia().load();
		refreshMoneyTop = new RefreshMoneyTop().load();

		loadCurrencyFormatter();

		if (config.getBoolean("use_vault"))
			setupVault();

		getServer().getPluginManager().registerEvents(new EconomyPlayerListener(), getInstance());

		getCommand("money").setExecutor(new SolaryCommand("money"));

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
			PlaceholdersHook.install();

		checkUpdate();
	}

	@Override
	public void onDisable() {
		economia.saveAll();
		database.endConnection();
		if (vaultEconomy != null)
			vaultEconomy.unregister();
	}

	private void setupDatabase() {
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

	private void setupVault() {
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

	public CurrencyFormatter loadCurrencyFormatter() {

		if (config.contains("abbreviations") && (config.getBoolean("abbreviations.enable.messages")
				|| config.getBoolean("abbreviations.enable.commands"))) {

			ConfigurationSection dictionarySection = config.getConfigurationSection("abbreviations.dictionary");
			if (dictionarySection != null) {
				getLogger().info("Carregando dicionário de abreviações:");

				TreeMap<BigDecimal, String> dictionary = new TreeMap<BigDecimal, String>();
				Map<String, String> displays = new HashMap<>();
				for (String key : dictionarySection.getKeys(false)) {
					ConfigurationSection value = dictionarySection.getConfigurationSection(key);
					dictionary.put(new BigDecimal(value.getDouble("divider")), key);

					if (value.getString("display") != null && !value.getString("display").isBlank())
						displays.put(key, value.getString("display"));

					getLogger().info("Carregando abreviação: " + key + ", " + value.getString("display") + ", "
							+ value.getDouble("divider"));

				}

				currencyFormatter = new CurrencyFormatter(dictionary, displays);
				getLogger().info("Sistema de abreviações carregado com sucesso.");
			}
		}

		return currencyFormatter;
	}

	public static String numberFormat(BigDecimal bigDecimal) {

		if (currencyFormatter != null && config.getBoolean("abbreviations.enable.messages")) {
			return currencyFormatter.abbreviate(bigDecimal);
		} else {
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
	}

	public static SolaryEconomy getInstance() {
		return instance;
	}

	public static RankAccount getMagnata() {
		return economia.getMagnata();
	}

	public static boolean isToggle(String account) {
		return economia.isToggle(account);
	}

	public static List<RankAccount> getMoneyTop() {
		return economia.getMoneyTop();
	}

	/**
	 * 
	 * Este trecho de código foi COPIADO! do projeto 'SrEmpregos' do SrBlecaute01.
	 * URL: https://github.com/SrBlecaute01/SrEmpregos
	 * 
	 * Agradecimentos ao VitorBlog pelo seu tutorial de como fazer as verificações
	 * de atualizações ;)
	 */
	private void checkUpdate() {
		CompletableFuture.runAsync(() -> {
			try {
				String link = "https://api.github.com/repos/sredition/Solary-Economy/releases/latest";
				String version = this.getDescription().getVersion();

				URL url = new URL(link);
				URLConnection connection = url.openConnection();
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					String response = reader.lines().collect(Collectors.joining("\n"));

					JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);
					String latestVersion = (String) jsonObject.get("tag_name");
					String download = (String) jsonObject.get("html_url");
					if (!version.equals(latestVersion)) {
						getLogger().info("");
						getLogger().warning("Você está usando uma versão desatualizada do plugin!");
						getLogger().info("");
						getLogger().info(" Uma nova versão está disponível!");
						getLogger().info(" Versão atual: " + version);
						getLogger().info(" Nova versão: " + latestVersion);
						getLogger().info("");
						getLogger().info(" Para baixar a versão mais recente abra o link abaixo:");
						getLogger().info(" " + download);
						getLogger().info("");

					} else {
						getLogger().info("Você está na versão mais recente do plugin");
					}
				}
			} catch (Exception xception) {
				getLogger().severe("Ocorreu um erro ao tentar verificar as atualizações: " + xception.getMessage());
			}

		}, ForkJoinPool.commonPool());
	}
}
