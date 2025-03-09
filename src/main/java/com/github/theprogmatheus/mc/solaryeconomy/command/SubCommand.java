package com.github.theprogmatheus.mc.solaryeconomy.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;


@Getter
@AllArgsConstructor
public abstract class SubCommand implements CommandExecutor {

    private String[] commands;
    private String permission;
    private String usage;

}
