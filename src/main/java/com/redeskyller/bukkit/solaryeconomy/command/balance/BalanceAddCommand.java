package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class BalanceAddCommand extends AbstractCommand {

    private final AbstractBalanceCommand parent;

    public BalanceAddCommand(AbstractBalanceCommand parent) {
        super(
                "add",
                parent.getPermission().concat(".add"),
                "add <player> <amount>",
                "Add a specific amount to a player's balance.",
                new String[]{}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
