package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.listener.PlayerJoinListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ListenerService implements Service {

    private final JavaPlugin plugin;

    public ListenerService(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void startup() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this.plugin);
    }

    @Override
    public void shutdown() {

    }
}
