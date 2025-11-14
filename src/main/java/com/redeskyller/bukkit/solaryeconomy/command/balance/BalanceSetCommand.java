package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class BalanceSetCommand extends AbstractCommand {

    private final AbstractBalanceCommand parent;

    public BalanceSetCommand(AbstractBalanceCommand parent) {
        super(
                "set",
                parent.getPermission().concat(".set"),
                "set <player> <amount>",
                "Set a player's balance to a specific amount.",
                new String[]{}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
