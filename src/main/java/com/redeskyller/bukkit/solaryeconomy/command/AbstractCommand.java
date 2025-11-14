package com.redeskyller.bukkit.solaryeconomy.command;

import lombok.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    private final List<AbstractCommand> subCommands = new ArrayList<>();
    private final String name;
    private final String permission;
    private final String usage;
    private final String description;
    private final String[] aliases;

    public void execute(CommandSender sender, Command command, String label, String[] args) {
    }

    public void execute(Player player, Command command, String label, String[] args) {
        this.execute((CommandSender) player, command, label, args);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (AbstractCommand subCommand : subCommands) {
                if (subCommand.getName().equalsIgnoreCase(args[0]) ||
                        List.of(subCommand.getAliases()).contains(args[0].toLowerCase())) {
                    String[] subArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, subArgs, 0, args.length - 1);
                    return subCommand.onCommand(sender, command, label, subArgs);
                }
            }
        }

        if (sender instanceof Player)
            execute((Player) sender, command, label, args);
        else
            execute(sender, command, label, args);
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            for (AbstractCommand subCommand : subCommands) {
                if (subCommand.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    completions.add(subCommand.getName());
                }
                for (String alias : subCommand.getAliases()) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase())) {
                        completions.add(alias);
                    }
                }
            }
            return completions;
        }

        if (args.length > 1) {
            for (AbstractCommand subCommand : subCommands) {
                if (subCommand.getName().equalsIgnoreCase(args[0]) ||
                        List.of(subCommand.getAliases()).contains(args[0].toLowerCase())) {
                    String[] subArgs = new String[args.length - 1];
                    System.arraycopy(args, 1, subArgs, 0, args.length - 1);
                    return subCommand.onTabComplete(sender, command, label, subArgs);
                }
            }
        }
        return List.of();
    }

}
