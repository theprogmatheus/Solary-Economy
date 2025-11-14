package com.redeskyller.bukkit.solaryeconomy.command.admin;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AdminReloadCommand extends AbstractCommand {

    public AdminReloadCommand() {
        super(
                "reload",
                "solaryeconomy.command.admin.reload",
                "reload",
                "Reload the SolaryEconomy configuration files.",
                new String[]{"rl"}
        );
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
