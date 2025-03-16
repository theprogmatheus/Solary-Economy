package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.cache.BankAccountCache;
import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.crud.EconomyCrud;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.util.CurrencyFormatter;
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
        this.accountCache = new BankAccountCache(this.economyCrud, Env.CACHE_ACCOUNTS_MAX_SIZE, Duration.ofSeconds(Env.CACHE_ACCOUNTS_EXPIRE), Duration.ofSeconds(Env.CACHE_ACCOUNTS_AUTO_FLUSH_DELAY));
        this.setupDefaultBank();
        this.accountCache.startup();
    }

    @Override
    public void shutdown() {
        this.accountCache.shutdown();
    }

    public String formatCurrency(BigDecimal value) {
        if (value.doubleValue() >= 0 && value.doubleValue() <= 1)
            return formatBigDecimal(value).concat(" ").concat(Env.ECONOMY_CURRENCY_SINGULAR);
        return formatBigDecimal(value).concat(" ").concat(Env.ECONOMY_CURRENCY_PLURAL);
    }

    public String formatBigDecimal(BigDecimal value) {
        if (Env.ECONOMY_CURRENCY_FORMATTER_WITH_TAG)
            return CurrencyFormatter.formatCurrencyWithTag(value, Env.ECONOMY_CURRENCY_FORMATTER_PRECISION);

        return CurrencyFormatter.formatCurrency(value, Env.ECONOMY_CURRENCY_FORMATTER_PRECISION);
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
                    return this.economyCrud.createBankAccount(DEFAULT_BANK_ID, accountId, name, new BigDecimal(Env.ECONOMY_START_VALUE));
            } finally {
                this.checkDefaultAccountLocks.remove(accountId);
            }
        }
        return false;
    }

}
