package com.redeskyller.bukkit.solaryeconomy.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    private String nameId;
    private UUID ownerId;
    private BigDecimal balance;
    private String economyId;
    private int flags;
}
