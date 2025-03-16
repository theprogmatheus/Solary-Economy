package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandRank extends AbstractCommand {

    protected CommandRank() {
        super(new String[]{"rank", "top"}, "Ranking of the richest players", "solaryeconomy.command.rank", "rank");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<BankAccountEntity> rank = SolaryEconomy.getInstance().getEconomyService().getEconomyCrud().readBankAccountsRank(10, 8);
        for (BankAccountEntity account : rank) {
            sender.sendMessage(account.getOwnerName() + ": " + account.getBalance().toPlainString());
        }
        return true;
    }
}
