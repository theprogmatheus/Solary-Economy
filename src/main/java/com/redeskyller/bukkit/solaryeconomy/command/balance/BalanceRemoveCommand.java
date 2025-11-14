package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class BalanceRemoveCommand extends AbstractCommand {

    private final AbstractBalanceCommand parent;

    public BalanceRemoveCommand(AbstractBalanceCommand parent) {
        super(
                "remove",
                parent.getPermission().concat(".remove"),
                "remove <player> <amount>",
                "Remove a specific amount from a player's balance.",
                new String[]{}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
