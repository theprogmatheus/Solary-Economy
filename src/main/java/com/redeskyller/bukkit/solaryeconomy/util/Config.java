package com.redeskyller.bukkit.solaryeconomy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

	public Config(Plugin plugin, String name) {
		this.plugin = plugin;
		this.name = name;
		reload();
	}

	private Plugin plugin;
	private String name;
	private File file;
	private YamlConfiguration yaml;

	public void reload() {
		try {
			if (this.name.contains("/")) {
				String[] split = this.name.split("/");
				if (split.length >= 2) {
					File folder = new File(split[0]);
					folder.mkdirs();
					this.file = new File(folder, split[1]);
				} else {
					this.name = this.name.replace("/", "");
					this.file = new File(this.plugin.getDataFolder(), this.name);
					if (!this.file.exists())
						this.plugin.saveResource(this.name, false);
				}
			} else {
				this.file = new File(this.plugin.getDataFolder(), this.name);
				if (!this.file.exists())
					this.plugin.saveResource(this.name, false);
			}
			this.yaml = YamlConfiguration
					.loadConfiguration(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
		} catch (Exception e) {
			System.out.println(this.file.getPath());
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			if (this.yaml != null && this.file != null) {
				this.yaml.save(this.file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getString(String path) {
		return this.yaml.getString(path).replace("&", "§");
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public String getName() {
		return name;
	}

	public File getFile() {
		return file;
	}

	public YamlConfiguration getYaml() {
		return yaml;
	}

}
