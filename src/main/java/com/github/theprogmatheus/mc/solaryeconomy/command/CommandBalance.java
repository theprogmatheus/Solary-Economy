package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBalance extends AbstractCommandExecutor {

    private final EconomyService economyService;

    public CommandBalance() {
        super(new String[]{"balance", "money", "bal", "coins"}, "balance", "balance <player>");
        this.economyService = SolaryEconomy.getInstance().getEconomyService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 && (sender instanceof Player)) {
            BankAccountEntity account = this.economyService.getDefaultAccountOrCreateIfNotExists((Player) sender);
            sender.sendMessage("§aSaldo: " + account.getBalance().toPlainString());
            return true;
        }

        if (args.length == 1) {
            BankAccountEntity account = this.economyService.getDefaultAccount(args[0].toLowerCase());
            if (account != null) {
                sender.sendMessage("§aSaldo de " + account.getOwnerName() + ": " + account.getBalance().toPlainString());
            } else {
                sender.sendMessage("§cA conta que você informou, não existe.");
            }
            return true;
        }


        return false;
    }
}
