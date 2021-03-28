package com.redeskyller.bukkit.solaryeconomy.hook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

import net.milkbowl.vault.chat.Chat;

public class VaultChat {

	public static String getPrefix(String playerName)
	{

		String prefix = "";
		try {
			RegisteredServiceProvider<Chat> chatclazz = Bukkit.getServicesManager().getRegistration(Chat.class);
			if (chatclazz != null) {
				Chat chat = chatclazz.getProvider();
				if (chat != null)
					try {

						Player player = Bukkit.getPlayer(playerName);

						if (player != null)
							prefix = chat.getPlayerPrefix(player).replace("&", "§");
						else
							prefix = chat
									.getPlayerPrefix(
											SolaryEconomy.config.getString(
													SolaryEconomy.getInstance().getConfig().getString("world")),
											playerName)
									.replace("&", "§");
					} catch (Exception exception) {
						prefix = "";
					}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return prefix;
	}

}
