package com.github.theprogmatheus.mc.solaryeconomy.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
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
                    if (value != null)
                        CompletableFuture.runAsync(() -> this.crud.updateBankAccount((BankAccountEntity) value));
                })
                .buildAsync(key -> this.crud.readBankAccount(key.getBankId(), key.getOwnerId()).orElse(null));
    }


}
