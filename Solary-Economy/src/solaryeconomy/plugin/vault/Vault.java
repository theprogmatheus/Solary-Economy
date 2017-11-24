package solaryeconomy.plugin.vault;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import solaryeconomy.SolaryEconomy;

public class Vault {

	public Vault() {
	}

	@SuppressWarnings("deprecation")
	public static String getPrefix(String player) {

		String prefix = "";
		try {
			RegisteredServiceProvider<Chat> chatclazz = Bukkit.getServicesManager().getRegistration(Chat.class);
			if (chatclazz != null) {
				Chat chat = chatclazz.getProvider();
				if (chat != null) {
					try {
						prefix = chat.getPlayerPrefix(SolaryEconomy.config.getString("world"), player).replace("&",
								"§");
					} catch (Exception e) {
						prefix = "";
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return prefix;
	}

}
