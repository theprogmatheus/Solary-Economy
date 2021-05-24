package com.redeskyller.bukkit.solaryeconomy.hook;

import org.bukkit.OfflinePlayer;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.util.NumbersUtils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholdersHook extends PlaceholderExpansion {

	private static final PlaceholdersHook instance;

	private static final String AUTHOR = "Sr_Edition";
	private static final String IDENTIFIER = "solaryeconomy";
	private static final String VERSION = SolaryEconomy.getInstance().getDescription().getVersion();

	static {
		instance = new PlaceholdersHook();
	}

	@Override
	public String onRequest(OfflinePlayer player, String placeholder)
	{

		switch (placeholder.toLowerCase()) {

		case "balance":
			return SolaryEconomy.numberFormat(SolaryEconomy.economia.getBalance(player.getName()));

		case "balance_formatted":
			return NumbersUtils.formatSimple(SolaryEconomy.economia.getBalance(player.getName()).doubleValue());

		default:
			return super.onRequest(player, placeholder);
		}

	}

	@Override
	public String getAuthor()
	{
		return AUTHOR;
	}

	@Override
	public String getIdentifier()
	{
		return IDENTIFIER;
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}

	public static PlaceholdersHook getInstance()
	{
		return instance;
	}

	public static void install()
	{
		getInstance().register();
	}
}
