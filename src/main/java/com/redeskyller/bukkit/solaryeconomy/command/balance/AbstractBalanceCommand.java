package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class AbstractBalanceCommand extends AbstractCommand {

    public AbstractBalanceCommand(String name, String usage, String description, String[] aliases) {
        super(name, String.format("solaryeconomy.command.%s", name), usage, description, aliases);

        getSubCommands().add(new BalanceHelpCommand(this));
        getSubCommands().add(new BalanceAddCommand(this));
        getSubCommands().add(new BalanceRemoveCommand(this));
        getSubCommands().add(new BalanceSetCommand(this));
        getSubCommands().add(new BalancePayCommand(this));
        getSubCommands().add(new BalanceTopCommand(this));
        getSubCommands().add(new BalanceToggleCommand(this));
        getSubCommands().add(new BalanceTycoonCommand(this));
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
