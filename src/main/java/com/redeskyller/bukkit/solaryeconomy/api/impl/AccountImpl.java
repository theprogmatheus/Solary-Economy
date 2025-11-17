package com.redeskyller.bukkit.solaryeconomy.api.impl;

import com.redeskyller.bukkit.solaryeconomy.api.Account;
import com.redeskyller.bukkit.solaryeconomy.api.Economy;
import com.redeskyller.bukkit.solaryeconomy.util.IntFlag;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountImpl implements Account {

    private long id;
    private final Economy economy;
    private final String nameId;
    private final UUID ownerId;
    private final BigDecimal balance;
    private final IntFlag flags;

    @Override
    public Economy getEconomy() {
        return this.economy;
    }

    @Override
    public String getNameId() {
        return this.nameId;
    }

    @Override
    public UUID getOwnerId() {
        return this.ownerId;
    }

    @Override
    public BigDecimal getBalance() {
        return this.balance;
    }

    @Override
    public boolean hasFlag(AccountFlag flag) {
        return this.flags.hasFlag(flag.getFlagValue());
    }

    @Override
    public void setFlag(AccountFlag flag, boolean value) {
        if (value)
            this.flags.addFlag(flag.getFlagValue());
        else
            this.flags.removeFlag(flag.getFlagValue());
    }
}
