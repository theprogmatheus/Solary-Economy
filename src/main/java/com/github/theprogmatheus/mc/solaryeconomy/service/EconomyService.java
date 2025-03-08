package com.github.theprogmatheus.mc.solaryeconomy.service;

import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankEntity;
import lombok.Getter;

import java.util.Optional;

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

    public void checkAccount(String nameId, String name) {
        if (!this.crud.existsBankAccount(this.defaultBankId, nameId))
            this.crud.createBankAccount(this.defaultBankId, nameId, name, 0);
    }

    public double getBalance(String name) {
        return this.crud.readBankAccount(this.defaultBankId, name).map(BankAccountEntity::getBalance).orElse(0.0);
    }

    public boolean deposit(String name, double value) {
        Optional<BankAccountEntity> optionalAccount = this.crud.readBankAccount(this.defaultBankId, name);
        if (optionalAccount.isPresent()) {
            BankAccountEntity account = optionalAccount.get();
            account.setBalance(account.getBalance() + value);
            return this.crud.updateBankAccount(account);
        }
        return false;
    }


}
