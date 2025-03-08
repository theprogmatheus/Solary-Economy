package com.github.theprogmatheus.mc.solaryeconomy;

import com.github.theprogmatheus.mc.solaryeconomy.command.MoneyCommand;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseManager;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import com.github.theprogmatheus.util.JGRUChecker;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static SolaryEconomy instance;

    private DatabaseManager databaseManager;
    private EconomyService economyService;
    private JGRUChecker updateChecker;


    @Override
    public void onLoad() {
        // setup instance
        instance = this;

        // load database and entities
        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.addEntityClass(BankEntity.class);
        this.databaseManager.addEntityClass(BankAccountEntity.class);

        // load economy service
        this.economyService = new EconomyService(this);
    }

    @Override
    public void onEnable() {
        this.databaseManager.startDatabase();
        this.economyService.setupEconomyService();

        getCommand("money").setExecutor(new MoneyCommand());

        // check for new updates
        this.checkNewUpdates();
    }

    @Override
    public void onDisable() {
        this.economyService.shutdownEconomyService();
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
