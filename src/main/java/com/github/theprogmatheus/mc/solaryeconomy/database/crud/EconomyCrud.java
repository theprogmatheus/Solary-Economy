package com.github.theprogmatheus.mc.solaryeconomy.database.crud;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.j256.ormlite.dao.Dao;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;


@Getter
public class EconomyCrud {

    private final Dao<BankEntity, Long> bankDao;
    private final Dao<BankAccountEntity, Long> bankAccountDao;

    public EconomyCrud() {
        this.bankDao = SolaryEconomy.getInstance().getDatabaseManager().getEntityDao(BankEntity.class, Long.class);
        this.bankAccountDao = SolaryEconomy.getInstance().getDatabaseManager().getEntityDao(BankAccountEntity.class, Long.class);
    }


    public boolean createBank(String nameId, String founder) {
        if (existsBank(nameId))
            throw new RuntimeException("An Bank already exists for the bankName provided. nameId: " + nameId + ", founder: " + founder);
        try {
            return this.bankDao.create(new BankEntity(0, nameId, nameId, founder, null)) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create Bank. bankName: " + nameId + ", bankOwner: " + founder, e);
        }
    }

    public Optional<BankEntity> readBank(String nameId) {
        try {
            return Optional.ofNullable(this.bankDao.queryBuilder().where().eq("name_id", nameId).queryForFirst());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to read Bank: " + nameId, e);
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

    public boolean existsBank(long bankId) {
        try {
            return this.bankDao.idExists(bankId);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to check if Bank exists. bankId: " + bankId, e);
        }
    }

    public boolean existsBank(String nameId) {
        try {
            return this.bankDao.queryBuilder().where().eq("name_id", nameId).countOf() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to check if Bank exists: " + nameId, e);
        }
    }

    // BANK ACCOUNTS


    public boolean createBankAccount(long bankId, String ownerId, String ownerName, BigDecimal initialBalance) {
        if (existsBankAccount(bankId, ownerId))
            throw new RuntimeException("An BankAccount already exists for the data provided. bankId: " + bankId + ", ownerId: " + ownerId);
        try {
            BankEntity bank = new BankEntity();
            bank.setId(bankId);
            return this.bankAccountDao.create(new BankAccountEntity(0, bank, ownerId, ownerName, initialBalance)) > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create BankAccount for bank. bankId: " + bankId + ", ownerId: " + ownerId + ", initialBalance: " + initialBalance, e);
        }
    }

    public Optional<BankAccountEntity> readBankAccount(long bankId, String ownerId) {
        try {
            return Optional.ofNullable(this.bankAccountDao.queryBuilder()
                    .where().eq("bank_id", bankId).and().eq("owner_id", ownerId)
                    .queryForFirst());
        } catch (SQLException e) {
            throw new RuntimeException("Unable to read BankAccount for bankId: " + bankId + ", ownerId: " + ownerId, e);
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

    public boolean existsBankAccount(long bankId, String ownerId) {
        try {
            return this.bankAccountDao.queryBuilder().where().eq("bank_id", bankId).and().eq("owner_id", ownerId).countOf() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to check if BankAccount exists. bankId: " + bankId + ", ownerId: " + ownerId, e);
        }
    }
}
