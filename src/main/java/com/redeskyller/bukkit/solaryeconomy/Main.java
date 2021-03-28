package com.redeskyller.bukkit.solaryeconomy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.redeskyller.bukkit.solaryeconomy.commands.SolaryCommand;

public class Main extends JavaPlugin {

	public static SolaryEconomy plugin;

	@Override
	public void onEnable()
	{
		plugin = new SolaryEconomy(this);
		plugin.onEnable();
		if (Bukkit.getPluginManager().isPluginEnabled(this))
			getCommand("money").setExecutor(new SolaryCommand("money"));
	}

	@Override
	public void onDisable()
	{
		plugin.onDisable();
	}

}
