package com.github.theprogmatheus.mc.solaryeconomy;

import com.github.theprogmatheus.mc.solaryeconomy.command.TestCommand;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static DatabaseManager databaseManager;

    @Override
    public void onLoad() {
        databaseManager = new DatabaseManager(this);
    }

    @Override
    public void onEnable() {
        databaseManager.setupDatabase();
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        databaseManager.shutdownDatabase();
    }

}
