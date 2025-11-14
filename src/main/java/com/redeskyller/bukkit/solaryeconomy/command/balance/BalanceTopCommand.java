package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class BalanceTopCommand extends AbstractCommand {

    private final AbstractBalanceCommand parent;

    public BalanceTopCommand(AbstractBalanceCommand parent) {
        super(
                "top",
                parent.getPermission().concat(".top"),
                "top",
                "Shows the top balances.",
                new String[]{"rank"}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
