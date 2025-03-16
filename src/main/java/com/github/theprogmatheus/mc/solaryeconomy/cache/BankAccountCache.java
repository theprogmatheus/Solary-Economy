package com.github.theprogmatheus.mc.solaryeconomy.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.crud.EconomyCrud;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.Service;
import lombok.Getter;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService.DEFAULT_BANK_ID;

@Getter
public class BankAccountCache implements Service {

    public final EconomyCrud crud;
    public final long maximumSize;
    public final Duration expireAfterAccess;
    public final AsyncLoadingCache<BankAccountKey, BankAccountEntity> accounts;
    private final List<BankAccountEntity> accountsRank;
    private final Duration autoFlushDelay;
    private ScheduledExecutorService autoFlushScheduler;
    private ScheduledExecutorService accountsRankScheduler;

    public BankAccountCache(EconomyCrud crud, long maximumSize, Duration expireAfterAccess, Duration autoFlushDelay) {
        this.crud = crud;
        this.maximumSize = maximumSize;
        this.expireAfterAccess = expireAfterAccess;
        this.accounts = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess)
                .removalListener(this::onCacheRemove)
                .buildAsync(this::onCacheLoad);
        this.autoFlushDelay = autoFlushDelay;
        this.accountsRank = new ArrayList<>();
    }

    private BankAccountEntity onCacheLoad(BankAccountKey key) {
        return this.crud.readBankAccount(key.getBankId(), key.getOwnerId()).orElse(null);
    }

    private void onCacheRemove(BankAccountKey key, BankAccountEntity account, RemovalCause cause) {
        CompletableFuture.runAsync(() -> this.crud.updateBankAccount(account));
    }

    public BankAccountEntity getDefaultAccount(String ownerId) {
        return getDefaultAccountAsync(ownerId).join();
    }

    public CompletableFuture<BankAccountEntity> getDefaultAccountAsync(String ownerId) {
        return getAccountAsync(DEFAULT_BANK_ID, ownerId);
    }

    public BankAccountEntity getAccount(long bankId, String ownerId) {
        return getAccountAsync(bankId, ownerId).join();
    }

    public CompletableFuture<BankAccountEntity> getAccountAsync(long bankId, String ownerId) {
        return this.accounts.get(new BankAccountKey(bankId, ownerId));
    }

    public BankAccountEntity put(BankAccountEntity account) {
        if (account != null && account.getBank() != null && account.getOwnerId() != null)
            this.accounts.put(new BankAccountKey(account.getBank().getId(), account.getOwnerId()), CompletableFuture.completedFuture(account));
        return account;
    }

    public void flush() {
        this.accounts.asMap().values().forEach(future -> future.whenComplete((account, t) -> this.crud.updateBankAccount(account)));
    }

    /*
     * Load accounts rank
     */
    public synchronized void loadAccountsRank() {

        Map<BankAccountKey, BankAccountEntity> mergedAccounts = new HashMap<>();

        // load from DB (accounts can be outdated)
        this.crud.readBankAccountsRank(Env.ECONOMY_RANK_SIZE, Env.ECONOMY_RANK_MAX_NAME_SIZE)
                .forEach(account ->
                        mergedAccounts.put(new BankAccountKey(account.getBank().getId(), account.getOwnerId()), account)
                );

        // load from Cache (accounts are updated) and merge
        this.accounts.asMap().values().stream().map(CompletableFuture::join).forEach(account -> {
            mergedAccounts.put(new BankAccountKey(account.getBank().getId(), account.getOwnerId()), account);
        });

        this.accountsRank.clear();
        this.accountsRank.addAll(mergedAccounts.values().stream()
                .filter(account -> account.getOwnerName().length() <= Env.ECONOMY_RANK_MAX_NAME_SIZE)
                .sorted(Comparator.comparing(BankAccountEntity::getBalance).reversed())
                .limit(Env.ECONOMY_RANK_SIZE)
                .collect(Collectors.toList()));
    }

    @Override
    public void startup() {
        if (this.autoFlushDelay != null && this.autoFlushScheduler == null)
            (this.autoFlushScheduler = Executors.newSingleThreadScheduledExecutor()).scheduleAtFixedRate(() -> CompletableFuture.runAsync(this::flush), this.autoFlushDelay.getSeconds(), this.autoFlushDelay.getSeconds(), TimeUnit.SECONDS);

        if (this.accountsRankScheduler == null)
            (this.accountsRankScheduler = Executors.newSingleThreadScheduledExecutor()).scheduleAtFixedRate(() -> CompletableFuture.runAsync(this::loadAccountsRank), 0L, Env.ECONOMY_RANK_UPDATE_DELAY, TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        if (this.autoFlushScheduler != null)
            this.autoFlushScheduler.shutdown();

        if (this.accountsRankScheduler != null)
            this.accountsRankScheduler.shutdown();

        this.flush();

        this.accounts.synchronous().invalidateAll();
        this.autoFlushScheduler = null;
        this.accountsRankScheduler = null;
    }
}
