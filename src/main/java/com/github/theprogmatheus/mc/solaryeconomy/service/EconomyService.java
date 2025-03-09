package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class EconomyService implements Service {

    public static final transient String DEFAULT_BANK_NAME = "default";
    public static final transient String DEFAULT_BANK_OWNER = "server";

    private EconomyCRUDService crud;
    private long defaultBankId;

    @Override
    public void startup() {
        this.crud = new EconomyCRUDService();
        this.crud.startup();

        // create default bank if not exists
        if (!this.crud.existsBank(DEFAULT_BANK_NAME))
            this.crud.createBank(DEFAULT_BANK_NAME, DEFAULT_BANK_OWNER);

        this.defaultBankId = this.crud.readBank(DEFAULT_BANK_NAME).map(BankEntity::getId).orElse(0L);
    }

    @Override
    public void shutdown() {
        this.crud.shutdown();
        this.crud = null;
    }

    public BankAccountEntity getAccount(String accountId) {
        return this.crud.readBankAccount(this.defaultBankId, accountId).orElse(null);
    }

    public void checkAccount(String accountId, String name) {
        if (!this.crud.existsBankAccount(this.defaultBankId, accountId))
            this.crud.createBankAccount(this.defaultBankId, accountId, name, new BigDecimal(0));
    }


}
