package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.j256.ormlite.dao.Dao;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;

public class MoneyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            String userId = (sender instanceof Player) ? sender.getName().toLowerCase() : sender.getName();

            // get dao
            Dao<BankEntity, Integer> bankDao = SolaryEconomy.getDatabaseManager().getEntityDao(BankEntity.class, int.class);
            Dao<BankAccountEntity, Integer> bankAccountDao = SolaryEconomy.getDatabaseManager().getEntityDao(BankAccountEntity.class, int.class);

            BankEntity bankEntity = bankDao.queryForEq("name", BankEntity.DEFAULT_BANK_NAME).stream().findFirst().orElse(null);
            // create default bank if not exists
            if (bankEntity == null)
                bankEntity = bankDao.createIfNotExists(new BankEntity(0, BankEntity.DEFAULT_BANK_NAME, BankEntity.DEFAULT_BANK_OWNER));

            System.out.println(bankEntity);

            BankAccountEntity bankAccountEntity = bankAccountDao.queryForEq("owner", userId).stream().findFirst().orElse(null);

            if (bankAccountEntity == null)
                bankAccountEntity = new BankAccountEntity(0, bankEntity.getName(), userId, 0);

            bankAccountEntity.setBalance(bankAccountEntity.getBalance() + 1);

            bankAccountDao.createOrUpdate(bankAccountEntity);

            sender.sendMessage("§aSaldo: " + bankAccountEntity.getBalance());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to run money command.", e);
        }
        return false;
    }
}
