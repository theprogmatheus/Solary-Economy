package com.github.theprogmatheus.mc.solaryeconomy.command.sub;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.command.SubCommand;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

public class CommandSet extends SubCommand {

    public CommandSet() {
        super(new String[]{"set"}, "set", "set <player> <newBalance>");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            String accountId = args[0].toLowerCase();

            BankAccountEntity account = SolaryEconomy.getInstance().getEconomyService().getAccount(accountId);
            if (account == null) {
                sender.sendMessage("§cConta não encontrada.");
                return false;
            }

            BigDecimal value = new BigDecimal(args[1]);

            if (value.doubleValue() < 0) {
                sender.sendMessage("§cNúmero inválido.");
                return false;
            }

            account.setBalance(value);

            SolaryEconomy.getInstance().getEconomyService().getCrud().updateBankAccount(account);

            sender.sendMessage("§aValor de " + account.getOwnerName() + " foi definido para " + value.toPlainString());
        }
        return false;
    }
}
