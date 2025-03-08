package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class MoneyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            String ownerId = (sender instanceof Player) ? ((Player) sender).getUniqueId().toString() : sender.getName();
            EconomyService service = SolaryEconomy.getInstance().getEconomyService();


            Optional<BankAccountEntity> optionalAccount = service.getCrud().readBankAccount(service.getDefaultBankId(), ownerId);

            if (optionalAccount.isPresent()) {
                BankAccountEntity account = optionalAccount.get();
                sender.sendMessage("§6Banco: " + account.getBank());

                sender.sendMessage("§6Contas do banco:");

                account.getBank().getAccounts().forEach(acc -> sender.sendMessage("§6" + acc));
            }


            // create account if not exists
            service.checkAccount(ownerId, sender.getName());


            service.deposit(ownerId, 5);
            sender.sendMessage("§aSaldo: " + service.getBalance(ownerId));
        } catch (Exception e) {
            throw new RuntimeException("Unable to run money command.", e);
        }
        return false;
    }
}
