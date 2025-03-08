package com.github.theprogmatheus.mc.solaryeconomy;

import com.github.theprogmatheus.mc.solaryeconomy.command.TestCommand;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseManager;
import com.github.theprogmatheus.mc.solaryeconomy.model.Counter;
import com.github.theprogmatheus.util.JGRUChecker;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static DatabaseManager databaseManager;

    private JGRUChecker updateChecker;

    @Override
    public void onLoad() {
        // load database and entities
        databaseManager = new DatabaseManager(this);
        databaseManager.addEntityClass(Counter.class);

        // load update checker
        this.checkNewUpdates();
    }

    @Override
    public void onEnable() {
        databaseManager.startDatabase();
        getCommand("test").setExecutor(new TestCommand());

    }

    @Override
    public void onDisable() {
        databaseManager.shutdownDatabase();
    }


    private void checkNewUpdates() {
        if (this.updateChecker == null)
            this.updateChecker = new JGRUChecker("theprogmatheus", getDescription().getName(), getDescription().getVersion());

        this.updateChecker.checkAsync().thenAcceptAsync(release -> {
            if (this.updateChecker.getCurrentVersion().equals(release.getVersion())) return;
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
