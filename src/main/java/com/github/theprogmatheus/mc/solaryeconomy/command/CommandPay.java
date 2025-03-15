package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.config.Lang;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import com.github.theprogmatheus.mc.solaryeconomy.util.InputParse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.text.MessageFormat;

public class CommandPay extends AbstractCommand {

    private final EconomyService economyService;

    protected CommandPay() {
        super(new String[]{"pay", "send"}, "Pay to another player", "pay", "pay <player> <value>");
        this.economyService = SolaryEconomy.getInstance().getEconomyService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (sender instanceof Player) {
                BankAccountEntity account = this.economyService.getDefaultAccountOrCreateIfNotExists((Player) sender);
                BankAccountEntity targetAccount = this.economyService.getDefaultAccount(InputParse.parseAccountId(args[0]));

                if (targetAccount == null) {
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

                if (account.getBalance().doubleValue() < value.doubleValue()) {
                    sender.sendMessage("§cVocê não tem saldo suficente para isso.");
                    return true;
                }

                account.setBalance(account.getBalance().subtract(value));
                targetAccount.setBalance(targetAccount.getBalance().add(value));

                account = this.economyService.saveInCache(account);
                targetAccount = this.economyService.saveInCache(targetAccount);

                sender.sendMessage("§aVocê pagou " + value.toPlainString() + " ao jogador " + targetAccount.getOwnerName());
            }
            return true;
        }
        return false;
    }
}
