package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CommandSeco extends AbstractCommandExecutor {

    public CommandSeco() {
        super(new String[]{"seco", "solaryeconomy"}, "solaryeconomy", "seco");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SolaryEconomy plugin = SolaryEconomy.getInstance();
        sender.sendMessage("§eRunning " + plugin.getName() + " on " + plugin.getDescription().getVersion() + " version");
        sender.sendMessage("§eAuthors: " + Arrays.toString(plugin.getDescription().getAuthors().toArray()));
        return false;
    }


}
