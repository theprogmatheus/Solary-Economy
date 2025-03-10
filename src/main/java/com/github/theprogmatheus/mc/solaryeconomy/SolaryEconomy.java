package com.github.theprogmatheus.mc.solaryeconomy;

import com.github.theprogmatheus.mc.solaryeconomy.command.MainCommand;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseManager;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.github.theprogmatheus.mc.solaryeconomy.listener.PlayerJoinListener;
import com.github.theprogmatheus.mc.solaryeconomy.service.ConfigurationService;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import com.github.theprogmatheus.mc.solaryeconomy.service.Service;
import com.github.theprogmatheus.util.JGRUChecker;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static SolaryEconomy instance;

    private DatabaseManager databaseManager;
    private JGRUChecker updateChecker;

    private Service[] services;
    private ConfigurationService configurationService;
    private EconomyService economyService;


    @Override
    public void onLoad() {
        // setup instance
        instance = this;

        // load database and entities
        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.addEntityClass(BankEntity.class);
        this.databaseManager.addEntityClass(BankAccountEntity.class);

        // database startup must to be the first instruction
        this.databaseManager.startDatabase();


        // load services
        this.services = new Service[]{
                this.configurationService = new ConfigurationService(this),
                this.economyService = new EconomyService(this)
        };
    }

    @Override
    public void onEnable() {

        // startup services
        for (Service service : this.services) {
            service.startup();
        }

        getCommand("money").setExecutor(new MainCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // check for new updates
        this.checkNewUpdates();
    }

    @Override
    public void onDisable() {

        // shutdown services
        for (Service service : this.services) {
            service.shutdown();
        }


        // database shutdown must be the last instruction
        this.databaseManager.shutdownDatabase();
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
