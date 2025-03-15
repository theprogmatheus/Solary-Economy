package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CommandSeco extends AbstractCommand {

    public CommandSeco() {
        super(new String[]{"seco", "solaryeconomy"}, "Default plugin command", null, "seco");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SolaryEconomy plugin = SolaryEconomy.getInstance();
        sender.sendMessage(" ");
        sender.sendMessage("§6Running §f" + plugin.getName() + "§6 on §f" + plugin.getDescription().getVersion() + "§6 version");
        sender.sendMessage("§6Authors: §f" + Arrays.toString(plugin.getDescription().getAuthors().toArray()));
        sender.sendMessage(" ");
        return false;
    }


}
