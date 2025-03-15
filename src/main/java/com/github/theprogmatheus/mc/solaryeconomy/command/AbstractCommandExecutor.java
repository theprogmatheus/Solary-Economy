package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
public abstract class AbstractCommandExecutor extends BukkitCommand implements CommandExecutor, TabCompleter {

    private static final CommandMap commandMap;

    static {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    private final String[] commands;

    protected AbstractCommandExecutor(String[] commands, String permission, String usage) {
        super(commands[0]);

        this.commands = commands;
        super.setUsage(usage);
        super.setPermission(permission);
        super.setPermissionMessage("§cVocê não tem permissão para isso.");
        if (commands.length > 1)
            super.setAliases(new ArrayList<>(Arrays.asList(Arrays.copyOfRange(commands, 1, commands.length))));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return onCommand(sender, this, commandLabel, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }

    public static <C extends AbstractCommandExecutor> C register(C command) {
        if (command == null) return null;
        commandMap.register(SolaryEconomy.getInstance().getName().toLowerCase(), command);
        return command;
    }
}
