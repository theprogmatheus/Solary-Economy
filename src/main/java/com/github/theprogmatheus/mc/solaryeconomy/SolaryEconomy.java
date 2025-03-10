package com.github.theprogmatheus.mc.solaryeconomy;

import com.github.theprogmatheus.mc.solaryeconomy.service.*;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayDeque;
import java.util.Deque;

@Getter
public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static SolaryEconomy instance;
    private final Deque<Service> services;

    /*
     * Setup plugin instance and services array
     */
    public SolaryEconomy() {
        SolaryEconomy.instance = this;
        this.services = new ArrayDeque<>();
    }

    /*
     * Service references
     */
    private ConfigurationService configurationService;
    private DatabaseService databaseService;
    private EconomyService economyService;
    private CommandService commandService;
    private ListenerService listenerService;
    private UpdaterService updaterService;

    /*
     * Load plugin services
     */
    @Override
    public void onLoad() {
        this.services.add(this.configurationService = new ConfigurationService(this));
        this.services.add(this.databaseService = new DatabaseService(this));
        this.services.add(this.economyService = new EconomyService(this));
        this.services.add(this.commandService = new CommandService(this));
        this.services.add(this.listenerService = new ListenerService(this));
        this.services.add(this.updaterService = new UpdaterService(this));
    }

    /*
     * Startup plugin services
     */
    @Override
    public void onEnable() {
        for (Service service : this.services) {
            service.startup();
        }
    }

    /*
     * Shutdown plugin services
     */
    @Override
    public void onDisable() {
        while (!this.services.isEmpty()) {
            this.services.removeLast().shutdown();
        }
    }

}
