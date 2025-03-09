package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.cache.BankAccountCache;
import com.github.theprogmatheus.mc.solaryeconomy.database.crud.EconomyCrud;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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

    private final JavaPlugin plugin;
    private final EconomyCrud crud;
    private final BankAccountCache accountCache;

    public EconomyService(JavaPlugin plugin) {
        this.plugin = plugin;
        this.crud = new EconomyCrud();
        this.accountCache = new BankAccountCache(this.crud, 10, Duration.ofMinutes(1), Duration.ofMinutes(5));
    }

    @Override
    public void startup() {
        this.setupDefaultBank();
        this.accountCache.startup();
    }

    @Override
    public void shutdown() {
        this.accountCache.shutdown();
    }

    public void setupDefaultBank() {
        if (!this.crud.existsBank(DEFAULT_BANK_ID))
            this.crud.createBank(DEFAULT_BANK_NAME, DEFAULT_BANK_OWNER);
    }


    public BankAccountEntity getDefaultAccountOrCreateIfNotExists(Player player) {
        BankAccountEntity account = getDefaultAccount(player.getName().toLowerCase());
        if (account != null) return account;
        this.crud.createBankAccount(DEFAULT_BANK_ID, player.getName().toLowerCase(), player.getName(), new BigDecimal(0));
        return getDefaultAccount(player.getName().toLowerCase());
    }

    public BankAccountEntity getDefaultAccount(String accountId) {
        return this.accountCache.getAccount(DEFAULT_BANK_ID, accountId);
    }

    public BigDecimal getBalance(String accountId) {
        return getBalance(DEFAULT_BANK_ID, accountId);
    }

    public BigDecimal getBalance(long bankId, String accountId) {
        BankAccountEntity account = this.accountCache.getAccount(bankId, accountId);
        return account != null ? account.getBalance() : new BigDecimal(0);
    }

    public BigDecimal withdraw(String accountId, BigDecimal value) {
        return withdraw(DEFAULT_BANK_ID, accountId, value);
    }

    public BigDecimal withdraw(long bankId, String accountId, BigDecimal value) {
        BankAccountEntity account = this.accountCache.getAccount(bankId, accountId);
        if (account == null) return new BigDecimal(0);
        account.setBalance(account.getBalance().subtract(value));
        return this.accountCache.put(account).getBalance();
    }

    public BigDecimal deposit(String accountId, BigDecimal value) {
        return deposit(DEFAULT_BANK_ID, accountId, value);
    }

    public BigDecimal deposit(long bankId, String accountId, BigDecimal value) {
        BankAccountEntity account = this.accountCache.getAccount(bankId, accountId);
        if (account == null) return new BigDecimal(0);
        account.setBalance(account.getBalance().add(value));
        return this.accountCache.put(account).getBalance();
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
