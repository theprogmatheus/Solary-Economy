package com.redeskyller.bukkit.solaryeconomy;

import com.redeskyller.bukkit.solaryeconomy.bootstrap.SolaryEconomyBootstrap;
import org.bukkit.plugin.java.JavaPlugin;

public class SolaryEconomy extends JavaPlugin {

    private SolaryEconomyBootstrap bootstrap;

    @Override
    public void onLoad() {
        this.bootstrap = new SolaryEconomyBootstrap(this);
    }

    @Override
    public void onEnable() {
        this.bootstrap.enable();
    }

    @Override
    public void onDisable() {
        this.bootstrap.disable();
    }
}
