package com.github.theprogmatheus.mc.solaryeconomy.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandBalance extends AbstractCommandExecutor {

    public CommandBalance() {
        super(new String[]{"balance"}, "balance", "balance <player>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
