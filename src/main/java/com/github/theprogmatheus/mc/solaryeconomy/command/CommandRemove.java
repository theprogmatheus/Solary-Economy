package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import com.github.theprogmatheus.mc.solaryeconomy.util.InputParse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

public class CommandRemove extends AbstractCommand {

    private final EconomyService economyService;

    public CommandRemove() {
        super(new String[]{"remove"}, "Remove players balance", "remove", "remove <player> <value>");
        this.economyService = SolaryEconomy.getInstance().getEconomyService();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 2) {
            BankAccountEntity account = this.economyService.getDefaultAccount(InputParse.parseAccountId(args[0]));
            if (account == null) {
                sender.sendMessage("§aA conta que você informou, não existe.");
                return true;
            }
            BigDecimal value = InputParse.parseBigDecimal(args[1]);
            if (value == null || value.doubleValue() <= 0) {
                sender.sendMessage("§cValor inválido.");
                return true;
            }

            account.setBalance(account.getBalance().subtract(value));
            account = this.economyService.saveInCache(account);

            sender.sendMessage("§aVocê removeu " + value.toPlainString() + " do saldo de " + account.getOwnerName());
            return true;
        }
        return false;
    }
}
