package com.redeskyller.bukkit.solaryeconomy.command.economy;

import com.redeskyller.bukkit.solaryeconomy.command.AbstractCommand;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@Getter
public class EconomyTycoonCommand extends AbstractCommand {

    private final AbstractEconomyCommand parent;

    public EconomyTycoonCommand(AbstractEconomyCommand parent) {
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
