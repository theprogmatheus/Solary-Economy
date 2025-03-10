package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.util.JGRUChecker;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@Getter
public class UpdaterService implements Service {

    private final JavaPlugin plugin;
    private JGRUChecker updateChecker;

    public UpdaterService(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public void startup() {

        // check for new updates
        this.checkNewUpdates();
    }

    @Override
    public void shutdown() {

    }


    private void checkNewUpdates() {
        if (this.updateChecker == null)
            this.updateChecker = new JGRUChecker("theprogmatheus", this.plugin.getDescription().getName(), this.plugin.getDescription().getVersion());

        this.updateChecker.checkAsync().thenAcceptAsync(release -> {
            if (release == null || this.updateChecker.getCurrentVersion().equals(release.getVersion())) return;
            Logger log = this.plugin.getLogger();

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
