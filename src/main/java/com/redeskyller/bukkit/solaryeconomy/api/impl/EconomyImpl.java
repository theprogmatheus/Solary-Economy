package com.redeskyller.bukkit.solaryeconomy.api.impl;

import com.redeskyller.bukkit.solaryeconomy.api.Economy;
import com.redeskyller.bukkit.solaryeconomy.util.IntFlag;
import lombok.Data;

@Data
public class EconomyImpl implements Economy {

    private long id;
    private final String nameId;
    private final String name;
    private final String description;
    private final String command;
    private final String[] commandAliases;
    private final String currencySymbol;
    private final String currencyName;
    private final String currencyNamePlural;
    private final IntFlag flags;


    @Override
    public String getNameId() {
        return this.nameId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getCommand() {
        return this.command;
    }

    @Override
    public String[] getCommandAliases() {
        return this.commandAliases;
    }

    @Override
    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    @Override
    public String getCurrencyName() {
        return this.currencyName;
    }

    @Override
    public String getCurrencyNamePlural() {
        return this.currencyNamePlural;
    }

    @Override
    public boolean hasFlag(EconomyFlag flag) {
        return this.flags.hasFlag(flag.getFlagValue());
    }

    @Override
    public void setFlag(EconomyFlag flag, boolean value) {
        if (value)
            this.flags.addFlag(flag.getFlagValue());
        else
            this.flags.removeFlag(flag.getFlagValue());
    }
}
