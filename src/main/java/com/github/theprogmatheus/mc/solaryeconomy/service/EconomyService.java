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

    private EconomyCrud economyCrud;
    private BankAccountCache accountCache;

    public EconomyService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void startup() {
        this.economyCrud = new EconomyCrud();
        this.accountCache = new BankAccountCache(this.economyCrud, 10, Duration.ofMinutes(1), Duration.ofMinutes(5));
        this.setupDefaultBank();
        this.accountCache.startup();
    }

    @Override
    public void shutdown() {
        this.accountCache.shutdown();
    }

    private void setupDefaultBank() {
        if (!this.economyCrud.existsBank(DEFAULT_BANK_ID))
            this.economyCrud.createBank(DEFAULT_BANK_NAME, DEFAULT_BANK_OWNER);
    }

    public BankAccountEntity saveInCache(BankAccountEntity account) {
        return this.accountCache.put(account);
    }

    public BankAccountEntity getDefaultAccountOrCreateIfNotExists(Player player) {
        BankAccountEntity account = getDefaultAccount(player.getName().toLowerCase());
        if (account != null) return account;
        this.economyCrud.createBankAccount(DEFAULT_BANK_ID, player.getName().toLowerCase(), player.getName(), new BigDecimal(0));
        return getDefaultAccount(player.getName().toLowerCase());
    }

    public BankAccountEntity getDefaultAccount(String accountId) {
        return this.accountCache.getAccount(DEFAULT_BANK_ID, accountId);
    }


    public void createDefaultAccountIfNotExistsAsync(Player player) {
        CompletableFuture.runAsync(() -> this.createDefaultAccountIfNotExists(player.getName().toLowerCase(), player.getName()));
    }

    private final Map<String, Object> checkDefaultAccountLocks = new ConcurrentHashMap<>();

    public boolean createDefaultAccountIfNotExists(String accountId, String name) {
        Object lock = this.checkDefaultAccountLocks.computeIfAbsent(accountId, k -> new Object());
        synchronized (lock) {
            try {
                if (!this.economyCrud.existsBankAccount(DEFAULT_BANK_ID, accountId))
                    return this.economyCrud.createBankAccount(DEFAULT_BANK_ID, accountId, name, new BigDecimal(0));
            } finally {
                this.checkDefaultAccountLocks.remove(accountId);
            }
        }
        return false;
    }

}
