package com.github.theprogmatheus.mc.solaryeconomy;

import com.github.theprogmatheus.mc.solaryeconomy.command.MainCommand;
import com.github.theprogmatheus.mc.solaryeconomy.listener.PlayerJoinListener;
import com.github.theprogmatheus.mc.solaryeconomy.service.*;
import com.github.theprogmatheus.util.JGRUChecker;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Logger;

@Getter
public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static SolaryEconomy instance;

    private JGRUChecker updateChecker;

    private final Deque<Service> services = new ArrayDeque<>();

    private ConfigurationService configurationService;
    private DatabaseService databaseService;
    private EconomyService economyService;
    private CommandService commandService;


    @Override
    public void onLoad() {
        // setup instance
        instance = this;

        // load services
        this.services.add(this.configurationService = new ConfigurationService(this));
        this.services.add(this.databaseService = new DatabaseService(this));
        this.services.add(this.economyService = new EconomyService(this));
        this.services.add(this.commandService = new CommandService(this));
    }

    @Override
    public void onEnable() {

        // startup services
        for (Service service : this.services) {
            service.startup();
        }

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // check for new updates
        this.checkNewUpdates();
    }

    @Override
    public void onDisable() {

        // shutdown services
        while (!this.services.isEmpty()) {
            this.services.removeLast().shutdown();
        }
    }


    private void checkNewUpdates() {
        if (this.updateChecker == null)
            this.updateChecker = new JGRUChecker("theprogmatheus", getDescription().getName(), getDescription().getVersion());

        this.updateChecker.checkAsync().thenAcceptAsync(release -> {
            if (release == null || this.updateChecker.getCurrentVersion().equals(release.getVersion())) return;
            Logger log = getLogger();

            System.out.println(" ");
            log.info("===============================================");
            log.info("[!] A new update is available! [!]");
            log.info("===============================================");
            log.info(" * Plugin: " + release.getName());
            log.info(" * Current Version: " + this.updateChecker.getCurrentVersion());
            log.info(" * New Version: " + release.getVersion());
            log.info(" * Download here: " + release.getDownloadPage());
            log.info("===============================================");
            log.info("[!] Upgrade now to enjoy the latest features, improvements, and bug fixes!");
            log.info("[!] Staying updated ensures better performance and security.");
            log.info("===============================================");
            System.out.println(" ");
        });
    }

}
