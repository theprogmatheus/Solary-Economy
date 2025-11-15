package com.redeskyller.bukkit.solaryeconomy.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EconomyEntity {
    private long id;
    private String nameId;
    private String name;
    private String description;
    private String command;
    private String commandAliases;
    private String currencySymbol;
    private String currencyName;
    private String currencyNamePlural;
    private int flags;
}
