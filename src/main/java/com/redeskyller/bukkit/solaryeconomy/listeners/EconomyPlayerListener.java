package com.redeskyller.bukkit.solaryeconomy.listeners;

import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.config;
import static com.redeskyller.bukkit.solaryeconomy.SolaryEconomy.economia;

import java.math.BigDecimal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EconomyPlayerListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		if (!economia.existsAccount(event.getPlayer().getName())) {
			economia.createAccount(event.getPlayer().getName(), new BigDecimal(config.getDouble("start_value")));
		}else {
			
		}

	}
}
