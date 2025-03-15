package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.config.Lang;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import com.github.theprogmatheus.mc.solaryeconomy.util.InputParse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class CommandSet extends AbstractCommand {

    private final EconomyService economyService;

    public CommandSet() {
        super(new String[]{"set"}, "Set new player balance", "set", "set <player> <newBalance>");
        this.economyService = SolaryEconomy.getInstance().getEconomyService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            BankAccountEntity account = this.economyService.getDefaultAccount(InputParse.parseAccountId(args[0]));
            if (account == null) {
                sender.sendMessage(MessageFormat.format(Lang.ACCOUNT_NOT_FOUND, args[0]));
                return true;
            }

            BigDecimal value = InputParse.parseBigDecimal(args[1]);
            if (value == null) {
                sender.sendMessage(MessageFormat.format(Lang.INVALID_INPUT_VALUE, args[1]));
                return true;
            }
            if (value.doubleValue() <= 0) {
                sender.sendMessage(MessageFormat.format(Lang.VALUE_CANT_BE_NEGATIVE, value.toPlainString()));
                return true;
            }

            account.setBalance(value);
            account = this.economyService.saveInCache(account);

            sender.sendMessage(MessageFormat.format(Lang.BALANCE_SET_SUCCESS, value.toPlainString(), account.getOwnerName()));
        }

        return false;
    }
}
