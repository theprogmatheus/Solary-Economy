package com.redeskyller.bukkit.solaryeconomy.command.economy;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractEconomyCommand extends AbstractCommand {

    public AbstractEconomyCommand(String name, String usage, String description, String[] aliases) {
        super(name, String.format("solaryeconomy.command.%s", name), usage, description, aliases);

        getSubCommands().add(new EconomyHelpCommand(this));
        getSubCommands().add(new EconomyAddCommand(this));
        getSubCommands().add(new EconomyRemoveCommand(this));
        getSubCommands().add(new EconomySetCommand(this));
        getSubCommands().add(new EconomyPayCommand(this));
        getSubCommands().add(new EconomyTopCommand(this));
        getSubCommands().add(new EconomyToggleCommand(this));
        getSubCommands().add(new EconomyTycoonCommand(this));
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
