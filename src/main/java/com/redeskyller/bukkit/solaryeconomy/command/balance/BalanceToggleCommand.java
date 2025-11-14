package com.redeskyller.bukkit.solaryeconomy.command.balance;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class BalanceToggleCommand extends AbstractCommand {

    private final AbstractBalanceCommand parent;

    public BalanceToggleCommand(AbstractBalanceCommand parent) {
        super(
                "toggle",
                parent.getPermission().concat(".toggle"),
                "toggle",
                "Configure options that involve your balance.",
                new String[]{"settings", "config"}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
