package com.github.theprogmatheus.mc.solaryeconomy.command.sub;

import com.github.theprogmatheus.mc.solaryeconomy.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandRemove extends SubCommand {

    public CommandRemove() {
        super(new String[]{"remove"}, "remove", "remove <player> <value>");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
