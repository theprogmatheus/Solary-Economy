package solaryeconomy.manager;

import org.bukkit.plugin.Plugin;

import solaryeconomy.util.Config;

public class Mensagens {

	private Config config;
	private Plugin plugin;

	public Mensagens(Plugin plugin) {
		this.plugin = plugin;
		reload();
	}

	public void reload() {
		this.config = new Config(this.plugin, "mensagens.yml");
	}

	public String get(String string) {
		string = string.toUpperCase();
		return this.config.getString(string).replace("&", "§");
	}

}
