package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MainCommand implements CommandExecutor {

    private AbstractCommandExecutor[] subCommands;

    public MainCommand() {
        this.registerSubCommands(new CommandSet(), new CommandAdd(), new CommandRemove());
        this.economyService = SolaryEconomy.getInstance().getEconomyService();
    }

    private final EconomyService economyService;


    public void registerSubCommands(AbstractCommandExecutor... subCommands) {
        this.subCommands = subCommands;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (!(sender instanceof Player)) return false;

            BankAccountEntity account = this.economyService.getDefaultAccountOrCreateIfNotExists((Player) sender);

            sender.sendMessage("§aSaldo: " + account.getBalance().toPlainString());
            return false;
        }

        for (AbstractCommandExecutor subCommand : this.subCommands) {
            for (String alias : subCommand.getCommands()) {
                if (!args[0].equalsIgnoreCase(alias)) continue;
                return subCommand.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            }
        }

        return false;
    }
}
