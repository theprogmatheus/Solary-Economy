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
    private long id;
    private String nameId;
    private String name;
    private UUID ownerId;
    private BigDecimal balance;
    private long economyId;
    private int flags;
}
