package com.redeskyller.bukkit.solaryeconomy.api;

import lombok.Getter;

public interface Economy {

    String getNameId();

    String getName();

    String getDescription();

    String getCommand();

    String[] getCommandAliases();

    String getCurrencySymbol();

    String getCurrencyName();

    String getCurrencyNamePlural();

    boolean hasFlag(EconomyFlag flag);

    void setFlag(EconomyFlag flag, boolean value);

    @Getter
    enum EconomyFlag {

        DEFAULT(1),
        MULTI_SERVER(1 << 1);

        private final int flagValue;

        EconomyFlag(int flagValue) {
            this.flagValue = flagValue;
        }

    }

}
