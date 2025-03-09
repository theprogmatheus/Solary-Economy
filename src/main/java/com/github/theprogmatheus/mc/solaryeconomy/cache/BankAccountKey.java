package com.github.theprogmatheus.mc.solaryeconomy.cache;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BankAccountKey {

    private long bankId;
    private String ownerId;

    @Override
    public String toString() {
        return bankId + "." + ownerId;
    }
}
