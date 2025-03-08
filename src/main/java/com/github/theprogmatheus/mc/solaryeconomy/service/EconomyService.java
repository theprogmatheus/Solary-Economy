package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.j256.ormlite.dao.Dao;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;

@Getter
public class EconomyService {


    public static final String DEFAULT_BANK_NAME = "default";
    public static final String DEFAULT_BANK_OWNER = "server";

    private final JavaPlugin plugin;

    public EconomyService(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    private Dao<BankEntity, Long> bankDao;
    private Dao<BankAccountEntity, Long> bankAccountDao;

    private Cache<String, BankAccountEntity> accountCache;

    public void setupEconomyService() {
        this.bankDao = SolaryEconomy.getInstance().getDatabaseManager().getEntityDao(BankEntity.class, Long.class);
        this.bankAccountDao = SolaryEconomy.getInstance().getDatabaseManager().getEntityDao(BankAccountEntity.class, Long.class);


        this.accountCache = Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(10)).refreshAfterWrite(Duration.ofMinutes(1)).build(key -> {
            // bankName@accountOwner
            String[] bankNameAndAccountOwner = key.split("@");
            String bankName = bankNameAndAccountOwner[0];
            String accountOwner = bankNameAndAccountOwner[1];
            return this.bankAccountDao.queryBuilder().where().eq("bank", bankName).and().eq("owner", accountOwner).queryForFirst();
        });


    }


    public void shutdownEconomyService() {
        // todo
    }

    public BankAccountEntity createBankAccount(String accountOwner, double initialBalance) {
        return createBankAccount(DEFAULT_BANK_NAME, accountOwner, initialBalance);
    }

    public BankAccountEntity createBankAccount(String bankName, String accountOwner, double initialBalance) {
        if (!existsBank(bankName)) return null;

        // todo
        return null;
    }

    public BankAccountEntity getBankAccount(String accountOwner) {
        return getBankAccount(DEFAULT_BANK_NAME, accountOwner);
    }

    public BankAccountEntity getBankAccount(String bankName, String accountOwner) {
        // todo
        return null;
    }

    public BankAccountEntity updateBankAccount(BankAccountEntity bankAccount) {
        // todo
        return null;
    }

    public boolean deleteBankAccount(String accountOwner) {
        return deleteBankAccount(DEFAULT_BANK_NAME, accountOwner);
    }

    public boolean deleteBankAccount(String bankName, String accountOwner) {
        // todo
        return false;
    }

    public boolean existsBankAccount(String accountOwner) {
        return existsBankAccount(DEFAULT_BANK_NAME, accountOwner);
    }

    public boolean existsBankAccount(String bankName, String accountOwner) {
        // todo
        return false;
    }


    // BANKS


    public BankEntity createBank(String bankName) {
        return createBank(DEFAULT_BANK_OWNER, bankName);
    }

    public BankEntity createBank(String bankOwner, String bankName) {
        // todo
        return null;
    }

    public BankEntity getBank(String bankName) {
        return getBank(DEFAULT_BANK_OWNER, bankName);
    }

    public BankEntity getBank(String bankOwner, String bankName) {
        // todo
        return null;
    }

    public boolean deleteBank(String bankName) {
        return deleteBank(DEFAULT_BANK_OWNER, bankName);
    }

    public boolean deleteBank(String bankOwner, String bankName) {
        // todo
        return false;
    }


    public boolean existsBank(String bankName) {
        return existsBank(DEFAULT_BANK_OWNER, bankName);
    }

    public boolean existsBank(String bankOwner, String bankName) {
        // todo
        return false;
    }


}
