package com.github.theprogmatheus.mc.solaryeconomy.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.crud.EconomyCrud;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class EconomyCache {

    public final EconomyCrud crud;
    public final long maximumSize;
    public final Duration expireAfterAccess;

    public final LoadingCache<String, BankEntity> banks;
    public final LoadingCache<BankAccountKey, BankAccountEntity> accounts;

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
                .build(key -> this.crud.readBank(key).orElse(null));

        this.accounts = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess)
                .removalListener((key, value, cause) -> {
                    if (value != null)
                        CompletableFuture.runAsync(() -> this.crud.updateBankAccount((BankAccountEntity) value));
                })
                .build(key -> this.crud.readBankAccount(key.getBankId(), key.getOwnerId()).orElse(null));
    }


}
