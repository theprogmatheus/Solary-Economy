package com.github.theprogmatheus.mc.solaryeconomy.listener;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.CompletableFuture;

public class PlayerJoinListener implements Listener {


    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player != null)
            SolaryEconomy.getInstance().getEconomyService().checkDefaultAccountAsync(player);
    }
}
