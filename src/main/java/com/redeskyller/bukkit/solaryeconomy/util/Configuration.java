package com.redeskyller.bukkit.solaryeconomy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
@Deprecated
public class Configuration extends YamlConfiguration {

	private final JavaPlugin plugin;
	private final File file;

	public Configuration(final JavaPlugin plugin, final File file)
	{
		this.file = file;
		this.plugin = plugin;
	}

	public Configuration load()
	{
		try {

			if (!this.file.exists()) {
				this.plugin.getDataFolder().mkdirs();
				this.plugin.saveResource(this.file.getName(), false);
			}

			load(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return this;
	}

	public JavaPlugin getPlugin()
	{
		return this.plugin;
	}

	public File getFile()
	{
		return this.file;
	}
}
