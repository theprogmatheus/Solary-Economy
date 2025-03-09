package com.github.theprogmatheus.mc.solaryeconomy.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.database.crud.EconomyCrud;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.Service;
import lombok.Getter;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService.DEFAULT_BANK_ID;

@Getter
public class BankAccountCache implements Service {

    public final EconomyCrud crud;
    public final long maximumSize;
    public final Duration expireAfterAccess;
    public final AsyncLoadingCache<BankAccountKey, BankAccountEntity> accounts;
    private final Duration autoFlushDelay;
    private ScheduledExecutorService scheduler;

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
        SolaryEconomy.getInstance().getLogger().info("flush: Salvando todas as contas: " + this.accounts.asMap().size());
        this.accounts.asMap().values().forEach(future -> future.whenComplete((account, t) -> this.crud.updateBankAccount(account)));
    }

    @Override
    public void startup() {
        if (this.autoFlushDelay != null && this.scheduler == null)
            (this.scheduler = Executors.newSingleThreadScheduledExecutor()).scheduleAtFixedRate(() -> CompletableFuture.runAsync(this::flush), this.autoFlushDelay.getSeconds(), this.autoFlushDelay.getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void shutdown() {
        if (this.scheduler != null)
            this.scheduler.shutdown();

        this.flush();
        this.accounts.synchronous().invalidateAll();
        this.scheduler = null;
    }
}
