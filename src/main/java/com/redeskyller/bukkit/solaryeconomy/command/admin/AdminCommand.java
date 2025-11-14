package com.redeskyller.bukkit.solaryeconomy.command.admin;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AdminCommand extends AbstractCommand {

    public AdminCommand() {
        super(
                "solaryeconomy",
                "solaryeconomy.command.admin",
                "/solaryeconomy <subcommand>",
                "Administrative command for managing SolaryEconomy plugin settings and operations.",
                new String[]{"seco", "seconomy"}
        );

        getSubCommands().add(new AdminReloadCommand());
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
