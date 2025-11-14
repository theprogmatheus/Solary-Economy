package com.redeskyller.bukkit.solaryeconomy.bootstrap;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SolaryEconomyBootstrap {

    private final JavaPlugin plugin;
    private final ConfigurationBootstrap configurationBootstrap;
    private final DatabaseBootstrap databaseBootstrap;

    public SolaryEconomyBootstrap(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configurationBootstrap = new ConfigurationBootstrap(plugin);
        this.databaseBootstrap = new DatabaseBootstrap(plugin);
    }

    public void enable() {
        this.configurationBootstrap.initializeConfigurations();
        this.databaseBootstrap.initializeDatabase();
    }

    public void disable() {
        this.databaseBootstrap.shutdownDatabase();
    }
}
