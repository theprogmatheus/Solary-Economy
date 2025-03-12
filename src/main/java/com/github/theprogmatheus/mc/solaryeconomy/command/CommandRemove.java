package com.github.theprogmatheus.mc.solaryeconomy.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandRemove extends AbstractCommandExecutor {

    public CommandRemove() {
        super(new String[]{"remove"}, "remove", "remove <player> <value>");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
