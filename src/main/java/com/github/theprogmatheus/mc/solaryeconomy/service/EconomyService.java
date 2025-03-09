package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.cache.BankAccountKey;
import com.github.theprogmatheus.mc.solaryeconomy.cache.EconomyCache;
import com.github.theprogmatheus.mc.solaryeconomy.database.crud.EconomyCrud;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class EconomyService implements Service {

    public static final String DEFAULT_BANK_NAME = "default";
    public static final String DEFAULT_BANK_OWNER = "server";
    public static final long DEFAULT_BANK_ID = 1L;

    private EconomyCrud crud;
    private EconomyCache cache;

    @Override
    public void startup() {
        this.crud = new EconomyCrud();

        this.setupDefaultBank();
        this.cache = new EconomyCache(this.crud, 1_000, Duration.ofMinutes(10));
    }

    @Override
    public void shutdown() {
        this.crud = null;
    }


    public void setupDefaultBank() {
        if (!this.crud.existsBank(DEFAULT_BANK_ID))
            this.crud.createBank(DEFAULT_BANK_NAME, DEFAULT_BANK_OWNER);
    }

    public BankEntity getBank(String bankId) {
        return this.cache.banks.get(bankId).join();
    }

    public BankAccountEntity getAccount(String accountId) {
        return this.getAccount(DEFAULT_BANK_ID, accountId);
    }

    public BankAccountEntity getAccount(long bankId, String accountId) {
        return this.cache.accounts.get(new BankAccountKey(bankId, accountId)).join();
    }

    public BigDecimal getBalance(String accountId) {
        return getBalance(DEFAULT_BANK_ID, accountId);
    }

    public BigDecimal getBalance(long bankId, String accountId) {
        BankAccountEntity account = getAccount(bankId, accountId);
        return account != null ? account.getBalance() : new BigDecimal(0);
    }

    public BigDecimal withdraw(String accountId, BigDecimal value) {
        return withdraw(DEFAULT_BANK_ID, accountId, value);
    }

    public BigDecimal withdraw(long bankId, String accountId, BigDecimal value) {
        BankAccountEntity account = getAccount(bankId, accountId);
        if (account == null) return new BigDecimal(0);
        account.setBalance(account.getBalance().subtract(value));
        return account.getBalance();
    }

    public BigDecimal deposit(String accountId, BigDecimal value) {
        return deposit(DEFAULT_BANK_ID, accountId, value);
    }

    public BigDecimal deposit(long bankId, String accountId, BigDecimal value) {
        BankAccountEntity account = getAccount(bankId, accountId);
        if (account == null) return new BigDecimal(0);
        account.setBalance(account.getBalance().add(value));
        return account.getBalance();
    }


    public void checkDefaultAccountAsync(Player player) {
        CompletableFuture.runAsync(() -> this.checkDefaultAccount(player.getName().toLowerCase(), player.getName()));
    }

    private final Map<String, Object> checkDefaultAccountLocks = new ConcurrentHashMap<>();

    private void checkDefaultAccount(String accountId, String name) {
        Object lock = this.checkDefaultAccountLocks.computeIfAbsent(accountId, k -> new Object());
        synchronized (lock) {
            try {
                if (!this.crud.existsBankAccount(DEFAULT_BANK_ID, accountId))
                    this.crud.createBankAccount(DEFAULT_BANK_ID, accountId, name, new BigDecimal(0));
            } finally {
                this.checkDefaultAccountLocks.remove(accountId);
            }
        }
    }


}
