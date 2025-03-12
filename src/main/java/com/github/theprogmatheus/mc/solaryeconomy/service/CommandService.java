package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.command.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;

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
        register(this.commandSeco = new CommandSeco());
        register(this.commandBalance = new CommandBalance());
        register(this.commandSet = new CommandSet());
        register(this.commandAdd = new CommandAdd());
        register(this.commandRemove = new CommandRemove());
    }


    public void register(AbstractCommandExecutor executor) {
        if (executor == null) return;

        String[] commands = executor.getCommands();
        PluginCommand command = this.plugin.getCommand(commands[0]);
        if (command == null) return;

        command.setPermission(executor.getPermission());
        command.setPermissionMessage("§cVocê não tem permissão para usar isso.");
        command.setUsage(executor.getUsage());

        // aliasses does not working
        if (commands.length > 1)
            command.setAliases(new ArrayList<>(Arrays.asList(Arrays.copyOfRange(commands, 1, commands.length))));
        command.setExecutor(executor);
        command.setTabCompleter(executor);

    }


    @Override
    public void shutdown() {

    }
}
