package com.github.theprogmatheus.mc.solaryeconomy.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandRank extends AbstractCommand {

    protected CommandRank() {
        super(new String[]{"rank", "top"}, "Ranking of the richest players", "rank", "rank");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
