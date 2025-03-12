package com.github.theprogmatheus.mc.solaryeconomy.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandAdd extends AbstractCommandExecutor {

    public CommandAdd() {
        super(new String[]{"add"}, "add", "add <player> <value>");

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
