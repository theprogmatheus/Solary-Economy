package com.redeskyller.bukkit.solaryeconomy;

import com.redeskyller.bukkit.solaryeconomy.bootstrap.SolaryEconomyBootstrap;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static SolaryEconomyBootstrap bootstrap;

    @Override
    public void onLoad() {
        bootstrap = new SolaryEconomyBootstrap(this);
    }

    @Override
    public void onEnable() {
        bootstrap.enable();
    }

    @Override
    public void onDisable() {
        bootstrap.disable();
    }
}
