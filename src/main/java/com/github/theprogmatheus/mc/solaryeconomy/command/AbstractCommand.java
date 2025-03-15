package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.config.Lang;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class AbstractCommand extends BukkitCommand implements CommandExecutor, TabCompleter {

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

    protected AbstractCommand(String[] commands, String description, String permission, String usage) {
        super(commands[0]);

        this.commands = commands;

        super.setDescription(description);
        super.setPermission(permission);
        super.setUsage(usage);
        if (commands.length > 1)
            super.setAliases(new ArrayList<>(Arrays.asList(Arrays.copyOfRange(commands, 1, commands.length))));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (checkPermission(sender))
            return onCommand(sender, this, commandLabel, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0)
            return new ArrayList<>();

        String lastWord = args[args.length - 1];
        Player senderPlayer = (sender instanceof Player) ? (Player) sender : null;
        ArrayList<String> matchedPlayers = new ArrayList<String>();
        for (Player player : sender.getServer().getOnlinePlayers()) {
            String name = player.getName();
            if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord))
                matchedPlayers.add(name);
        }
        matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }

    private boolean checkPermission(CommandSender sender) {
        if (getPermission() == null || getPermission().isEmpty() || sender.hasPermission(getPermission())) return true;
        String message = getPermissionMessage() != null ? getPermissionMessage() : MessageFormat.format(Lang.COMMAND_NO_PERMISSION, getUsage(), getPermission());
        if (message != null && !message.isEmpty())
            sender.sendMessage(getPermissionMessage());
        return false;
    }

    public static <C extends AbstractCommand> C register(C command) {
        if (command == null) return null;
        commandMap.register(SolaryEconomy.getInstance().getName().toLowerCase(), command);
        return command;
    }
}
