package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import com.github.theprogmatheus.mc.solaryeconomy.util.InputParse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

public class CommandAdd extends AbstractCommandExecutor {

    private final EconomyService economyService;

    public CommandAdd() {
        super(new String[]{"add"}, "add", "add <player> <value>");
        this.economyService = SolaryEconomy.getInstance().getEconomyService();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            BankAccountEntity account = this.economyService.getDefaultAccount(InputParse.parseAccountId(args[0]));
            if (account == null) {
                sender.sendMessage("§aA conta que você informou, não existe.");
                return false;
            }
            BigDecimal value = InputParse.parseBigDecimal(args[1]);
            if (value == null || value.doubleValue() <= 0) {
                sender.sendMessage("§cValor inválido.");
                return false;
            }

            account.setBalance(account.getBalance().add(value));
            account = this.economyService.saveInCache(account);

            sender.sendMessage("§aVocê adicionou " + value.toPlainString() + " ao saldo de " + account.getOwnerName());
            return true;
        }

        return false;
    }
}
