package com.github.theprogmatheus.mc.solaryeconomy;

import com.github.theprogmatheus.mc.solaryeconomy.command.TestCommand;
import com.github.theprogmatheus.mc.solaryeconomy.database.DatabaseManager;
import com.github.theprogmatheus.mc.solaryeconomy.model.Counter;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class SolaryEconomy extends JavaPlugin {

    @Getter
    private static DatabaseManager databaseManager;

    @Override
    public void onLoad() {
        databaseManager = new DatabaseManager(this);
        databaseManager.addEntityClass(Counter.class);
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

}
