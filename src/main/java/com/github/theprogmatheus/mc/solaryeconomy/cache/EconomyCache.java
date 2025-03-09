package com.github.theprogmatheus.mc.solaryeconomy.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.theprogmatheus.mc.solaryeconomy.database.crud.EconomyCrud;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class EconomyCache {

    public final EconomyCrud crud;
    public final long maximumSize;
    public final Duration expireAfterAccess;

    public final AsyncLoadingCache<String, BankEntity> banks;
    public final AsyncLoadingCache<BankAccountKey, BankAccountEntity> accounts;


    public EconomyCache(EconomyCrud crud, long maximumSize, Duration expireAfterAccess) {
        this.crud = crud;
        this.maximumSize = maximumSize;
        this.expireAfterAccess = expireAfterAccess;

        this.banks = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess)
                .removalListener((key, value, cause) -> {
                    if (value != null)
                        CompletableFuture.runAsync(() -> this.crud.updateBank((BankEntity) value));
                })
                .buildAsync(key -> this.crud.readBank(key).orElse(null));

        this.accounts = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess)
                .removalListener((key, value, cause) -> {
                    System.out.println("Cache removed removalListener: " + value);
                    if (value != null)
                        CompletableFuture.runAsync(() -> this.crud.updateBankAccount((BankAccountEntity) value));
                })
                .buildAsync(key -> this.crud.readBankAccount(key.getBankId(), key.getOwnerId()).orElse(null));
    }


    public boolean putAccount(BankAccountEntity account) {
        if (account == null || account.getOwnerId() == null || account.getBank() == null) return false;
        this.accounts.put(new BankAccountKey(account.getBank().getId(), account.getOwnerId()), CompletableFuture.completedFuture(account));
        return true;
    }


    public void flushAccountsAsync() {
        this.accounts.asMap().values().forEach(af -> af.whenCompleteAsync((account, t) -> this.crud.updateBankAccount(account)));
    }

    public void flushAccounts() {
        this.accounts.asMap().values().forEach(af -> af.whenComplete((account, t) -> this.crud.updateBankAccount(account)));
    }
}
