package com.redeskyller.bukkit.solaryeconomy.bootstrap;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SolaryEconomyBootstrap {

    @Getter
    private static ConfigurationBootstrap configurationBootstrap;

    public static void bootstrap(JavaPlugin plugin) {
        configurationBootstrap = new ConfigurationBootstrap(plugin);
    }

    public static void enable(JavaPlugin plugin) {
        configurationBootstrap.initializeConfigurations();
    }

    public static void disable(JavaPlugin plugin) {

    }
}
