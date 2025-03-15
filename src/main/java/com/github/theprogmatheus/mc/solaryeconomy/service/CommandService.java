package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.command.*;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CommandService implements Service {

    private final JavaPlugin plugin;

    // Command Executors
    private CommandSeco commandSeco;
    private CommandBalance commandBalance;
    private CommandSet commandSet;
    private CommandAdd commandAdd;
    private CommandRemove commandRemove;

    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startup() {
        this.commandSeco = AbstractCommandExecutor.register(new CommandSeco());
        this.commandBalance = AbstractCommandExecutor.register(new CommandBalance());
        this.commandSet = AbstractCommandExecutor.register(new CommandSet());
        this.commandAdd = AbstractCommandExecutor.register(new CommandAdd());
        this.commandRemove = AbstractCommandExecutor.register(new CommandRemove());
    }

    @Override
    public void shutdown() {

    }
}
