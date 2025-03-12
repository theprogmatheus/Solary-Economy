package com.github.theprogmatheus.mc.solaryeconomy.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;


@Getter
@AllArgsConstructor
public abstract class AbstractCommandExecutor implements CommandExecutor, TabCompleter {

    private String[] commands;
    private String permission;
    private String usage;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }
}
