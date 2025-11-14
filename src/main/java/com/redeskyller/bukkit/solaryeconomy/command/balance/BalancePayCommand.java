package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class BalancePayCommand extends AbstractCommand {

    private final AbstractBalanceCommand parent;

    public BalancePayCommand(AbstractBalanceCommand parent) {
        super(
                "pay",
                parent.getPermission().concat(".pay"),
                "pay <player> <amount>",
                "Pay another player a specified amount of money.",
                new String[]{}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
