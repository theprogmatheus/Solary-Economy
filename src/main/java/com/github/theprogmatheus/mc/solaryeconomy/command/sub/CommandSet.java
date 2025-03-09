package com.github.theprogmatheus.mc.solaryeconomy.command.sub;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.math.BigDecimal;

public class CommandSet extends SubCommand {

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
                sender.sendMessage("§cConta não encontrada.");
                return false;
            }

            BigDecimal value = null;
            try {
                value = new BigDecimal(args[1]);
                if (value.doubleValue() < 0)
                    throw new RuntimeException("Value cant be negative number");
            } catch (Exception e) {
                sender.sendMessage("§cNúmero inválido: " + e.getMessage());
                return false;
            }
            account.setBalance(value);

            // save account
            this.economyService.getAccountCache().put(account);

            sender.sendMessage("§aValor de " + account.getOwnerName() + " foi definido para " + value.toPlainString());
        }
        return false;
    }
}
