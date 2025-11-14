package com.redeskyller.bukkit.solaryeconomy.api;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

public interface Account {

    Economy getEconomy();

    String getNameId();

    UUID getOwnerId();

    BigDecimal getBalance();

    boolean hasFlag(AccountFlag flag);

    void setFlag(AccountFlag flag, boolean value);

    @Getter
    enum AccountFlag {

        RECEIVING_FUNDS_DISABLED(1);

        private final int flagValue;

        AccountFlag(int flagValue) {
            this.flagValue = flagValue;
        }

    }
}
