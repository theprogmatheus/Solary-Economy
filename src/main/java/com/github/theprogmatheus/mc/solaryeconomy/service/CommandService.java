package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.command.AbstractCommand;
import com.github.theprogmatheus.mc.solaryeconomy.command.CommandBalance;
import com.github.theprogmatheus.mc.solaryeconomy.command.CommandSeco;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CommandService implements Service {

    private final JavaPlugin plugin;

    // Command Executors
    private CommandSeco commandSeco;
    private CommandBalance commandBalance;

    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startup() {
        this.commandSeco = AbstractCommand.register(new CommandSeco());
        this.commandBalance = AbstractCommand.register(new CommandBalance());
    }

    @Override
    public void shutdown() {

    }
}
