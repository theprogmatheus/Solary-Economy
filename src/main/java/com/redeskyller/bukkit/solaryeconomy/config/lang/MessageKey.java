package com.redeskyller.bukkit.solaryeconomy.config.lang;

import lombok.Getter;

@Getter
public enum MessageKey {

    PREFIX("prefix"),
    COMMAND_TEMPLATE("command.template");

    private final String path;
    private final boolean list;

    MessageKey(String path) {
        this(path, false);
    }

    MessageKey(String path, boolean list) {
        this.path = path;
        this.list = list;
    }

}