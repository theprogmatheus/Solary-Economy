package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.j256.ormlite.dao.Dao;
import lombok.Getter;

import java.sql.SQLException;
import java.util.Optional;


@Getter
public class EconomyDatabaseService implements Service {

    private Dao<BankEntity, Long> bankDao;
    private Dao<BankAccountEntity, Long> bankAccountDao;

    @Override
    public void startup() {
        this.bankDao = SolaryEconomy.getInstance().getDatabaseManager().getEntityDao(BankEntity.class, Long.class);
        this.bankAccountDao = SolaryEconomy.getInstance().getDatabaseManager().getEntityDao(BankAccountEntity.class, Long.class);
    }

    @Override
    public void shutdown() {

    }


    public BankAccountEntity createBankAccount(String bankName, String accountOwner, double initialBalance) {
        if (existsBankAccount(bankName, accountOwner))
            throw new RuntimeException("An BankAccount already exists for the data provided. bank: " + bankName + ", owner: " + accountOwner);

        try {
            return this.bankAccountDao.createIfNotExists(new BankAccountEntity(0, bankName, accountOwner, initialBalance));
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create BankAccount for bank: " + bankName + ", owner: " + accountOwner + ", initialBalance: " + initialBalance, e);
        }
    }

    public Optional<BankAccountEntity> readBankAccount(String bankName, String accountOwner) {
        try {
            return Optional.ofNullable(this.bankAccountDao.queryBuilder()
                    .where().eq("bank", bankName).and().eq("owner", accountOwner)
                    .queryForFirst());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to read BankAccount for bank: " + bankName + ", owner: " + accountOwner, e);
        }
    }

    public boolean updateBankAccount(BankAccountEntity bankAccount) {
        if (bankAccount == null)
            throw new RuntimeException("BankAccount cant be null");

        try {
            if (!this.bankAccountDao.idExists(bankAccount.getId()))
                throw new RuntimeException("This BankAccount has not been created yet");

            return this.bankAccountDao.update(bankAccount) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update BankAccount: " + bankAccount, e);
        }
    }

    public boolean deleteBankAccount(BankAccountEntity bankAccount) {
        if (bankAccount == null)
            throw new RuntimeException("BankAccount cant be null");

        try {
            if (!this.bankAccountDao.idExists(bankAccount.getId()))
                throw new RuntimeException("This BankAccount has not been created yet");

            return this.bankAccountDao.delete(bankAccount) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete BankAccount: " + bankAccount, e);
        }
    }


    public boolean existsBankAccount(String bankName, String accountOwner) {
        try {
            return this.bankAccountDao.queryBuilder().where().eq("bank", bankName).and().eq("owner", accountOwner).countOf() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to check if BankAccount exists: " + bankName + ", owner: " + accountOwner, e);
        }
    }


    public BankEntity createBank(String bankName, String bankOwner) {
        if (existsBank(bankName))
            throw new RuntimeException("An Bank already exists for the bankName provided. bankName: " + bankName + ", bankOwner: " + bankOwner);

        try {
            return this.bankDao.createIfNotExists(new BankEntity(0, bankName, bankOwner));
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create Bank. bankName: " + bankName + ", bankOwner: " + bankOwner, e);
        }
    }

    public Optional<BankEntity> readBank(String bankName) {
        try {
            return Optional.ofNullable(this.bankDao.queryBuilder().where().eq("name", bankName).queryForFirst());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to read Bank: " + bankName, e);
        }
    }

    public boolean updateBank(BankEntity bank) {
        if (bank == null) throw new RuntimeException("Bank cant be null");
        try {

            if (!this.bankDao.idExists(bank.getId()))
                throw new RuntimeException("This Bank has not been created yet");

            return this.bankDao.update(bank) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update bank: " + bank, e);
        }
    }


    public boolean deleteBank(BankEntity bank) {
        if (bank == null) throw new RuntimeException("Bank cant be null");
        try {
            if (!this.bankDao.idExists(bank.getId()))
                throw new RuntimeException("This Bank has not been created yet");

            return this.bankDao.delete(bank) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete bank: " + bank, e);
        }
    }


    public boolean existsBank(String bankName) {
        try {
            return this.bankDao.queryBuilder().where().eq("name", bankName).countOf() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to check if Bank exists: " + bankName, e);
        }
    }


}
