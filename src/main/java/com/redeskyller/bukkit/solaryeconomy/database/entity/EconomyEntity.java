package com.redeskyller.bukkit.solaryeconomy.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EconomyEntity {
    private String nameId;
    private String name;
    private String description;
    private String command;
    private String commandAliases;
    private String currencySimbol;
    private String currencyName;
    private String currencyNamePlural;
    private int flags;
}
