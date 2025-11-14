package com.redeskyller.bukkit.solaryeconomy.command.economy;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class EconomyHelpCommand extends AbstractCommand {

    private final AbstractEconomyCommand parent;

    public EconomyHelpCommand(AbstractEconomyCommand parent) {
        super(
                "help",
                parent.getPermission().concat(".help"),
                "help",
                "Display help information about the money commands.",
                new String[]{"ajuda", "?"}
        );
        this.parent = parent;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        // TODO
    }
}
