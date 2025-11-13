package com.redeskyller.bukkit.solaryeconomy;

import com.redeskyller.bukkit.solaryeconomy.bootstrap.SolaryEconomyBootstrap;
import org.bukkit.plugin.java.JavaPlugin;

public class SolaryEconomy extends JavaPlugin {

    @Override
    public void onLoad() {
        SolaryEconomyBootstrap.bootstrap(this);
    }

    @Override
    public void onEnable() {
        SolaryEconomyBootstrap.enable(this);
    }

    @Override
    public void onDisable() {
        SolaryEconomyBootstrap.disable(this);
    }
}
