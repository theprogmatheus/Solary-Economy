package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.config.Lang;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class CommandSet extends AbstractCommandExecutor {

    public CommandSet() {
        super(new String[]{"set"}, "set", "set <player> <newBalance>");
        this.economyService = SolaryEconomy.getInstance().getEconomyService();
    }

    private final EconomyService economyService;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            String accountId = args[0].toLowerCase();


            BankAccountEntity account = this.economyService.getDefaultAccount(accountId);
            if (account == null) {
                sender.sendMessage(MessageFormat.format(Lang.ACCOUNT_NOT_FOUND, accountId));
                return false;
            }

            BigDecimal value = null;
            try {
                value = new BigDecimal(args[1]);
                if (value.doubleValue() < 0)
                    throw new RuntimeException(MessageFormat.format(Lang.VALUE_CANT_BE_NEGATIVE, value));
            } catch (Exception e) {
                sender.sendMessage(MessageFormat.format(Lang.INVALID_VALUE, args[1], e.getMessage()));
                return false;
            }
            account.setBalance(value);

            // save account
            this.economyService.getAccountCache().put(account);

            sender.sendMessage(MessageFormat.format(Lang.BALANCE_SET_SUCCESS, account.getOwnerName(), account.getBalance().toPlainString()));
        }
        return false;
    }
}
