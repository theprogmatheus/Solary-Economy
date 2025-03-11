package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.hook.VaultEconomyHook;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HookLoaderService implements Service {


    private final JavaPlugin plugin;

    private VaultEconomyHook economyHook;

    public HookLoaderService(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void startup() {
        this.loadVaultEconomy();
    }

    @Override
    public void shutdown() {
        this.unloadVaultEconomy();
    }


    public void loadVaultEconomy() {
        Plugin vaultPlugin = Bukkit.getPluginManager().getPlugin("Vault");
        if (vaultPlugin != null)
            Bukkit.getServer().getServicesManager().register(Economy.class, this.economyHook = new VaultEconomyHook(this.plugin, SolaryEconomy.getInstance().getEconomyService()), SolaryEconomy.getInstance(),
                    ServicePriority.Highest);
    }

    public void unloadVaultEconomy() {
        if (this.economyHook != null)
            Bukkit.getServer().getServicesManager().unregister(this.economyHook);
    }

}
