package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class BalanceTycoonCommand extends AbstractCommand {

    private final AbstractBalanceCommand parent;

    public BalanceTycoonCommand(AbstractBalanceCommand parent) {
        super(
                "tycoon",
                parent.getPermission().concat(".tycoon"),
                "tycoon",
                "See who is the richest player on the server right now.",
                new String[]{"magnata"}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
