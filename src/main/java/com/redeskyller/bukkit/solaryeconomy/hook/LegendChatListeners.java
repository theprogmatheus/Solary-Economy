package com.redeskyller.bukkit.solaryeconomy.hook;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.redeskyller.bukkit.solaryeconomy.RankAccount;
import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
@Deprecated
public class LegendChatListeners implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEnable(ChatMessageEvent event)
	{
		if (event.isCancelled())
			return;

		boolean use_magnata = SolaryEconomy.config.getBoolean("magnata_tag");
		if (use_magnata) {
			String magnata_tag = SolaryEconomy.messages.get("MAGNATA_TAG");
			if (magnata_tag == null)
				magnata_tag = "§a[$]";

			RankAccount magnata = SolaryEconomy.economia.getMagnata();
			Player player = event.getSender();

			if ((magnata != null) && (player != null) && player.getName().equals(magnata.getName()))
				event.setTagValue("solary_economy_magnata", magnata_tag);

		}
	}

}
