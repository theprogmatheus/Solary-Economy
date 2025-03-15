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
        this.commandSeco = AbstractCommand.register(new CommandSeco());
        this.commandBalance = AbstractCommand.register(new CommandBalance());
        this.commandSet = AbstractCommand.register(new CommandSet());
        this.commandAdd = AbstractCommand.register(new CommandAdd());
        this.commandRemove = AbstractCommand.register(new CommandRemove());
    }

    @Override
    public void shutdown() {

    }
}
