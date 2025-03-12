package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.command.CommandSeco;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CommandService implements Service {

    private final JavaPlugin plugin;

    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startup() {
        this.plugin.getCommand("money").setExecutor(new CommandSeco());
    }

    @Override
    public void shutdown() {

    }
}
