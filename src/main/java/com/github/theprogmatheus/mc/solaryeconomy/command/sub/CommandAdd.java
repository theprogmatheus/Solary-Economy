package com.github.theprogmatheus.mc.solaryeconomy.command.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandAdd extends SubCommand {

    public CommandAdd() {
        super(new String[]{"add"}, "add", "add <player> <value>");

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
