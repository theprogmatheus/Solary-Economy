package com.redeskyller.bukkit.solaryeconomy.command.economy;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class EconomyToggleCommand extends AbstractCommand {

    private final AbstractEconomyCommand parent;

    public EconomyToggleCommand(AbstractEconomyCommand parent) {
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
