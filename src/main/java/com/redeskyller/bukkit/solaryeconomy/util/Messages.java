package com.redeskyller.bukkit.solaryeconomy.util;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;
@Deprecated
public class Messages {

	private Configuration config;
	private JavaPlugin plugin;

	public Messages(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	public Messages load()
	{
		this.config = new Configuration(this.plugin, new File(this.plugin.getDataFolder(), "mensagens.yml")).load();
		return this;
	}

	public String get(String string)
	{
		string = string.toUpperCase();
		return this.config.getString(string).replace("&", "§");
	}

}
