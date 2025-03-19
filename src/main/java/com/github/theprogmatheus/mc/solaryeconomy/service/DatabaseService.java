package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseManager;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.TycoonEntity;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public class DatabaseService implements Service {

    private final JavaPlugin plugin;
    private final DatabaseManager databaseManager;

    public DatabaseService(JavaPlugin plugin) {
        this.plugin = plugin;
        this.databaseManager = new DatabaseManager(plugin);
    }

    @Override
    public void startup() {

        // Add database entities
        this.databaseManager.addEntityClass(BankEntity.class);
        this.databaseManager.addEntityClass(BankAccountEntity.class);
        this.databaseManager.addEntityClass(TycoonEntity.class);

        // Start database
        this.databaseManager.startDatabase();
    }

    @Override
    public void shutdown() {

        // Shutdown database
        this.databaseManager.shutdownDatabase();
    }


}
