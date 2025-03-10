package com.github.theprogmatheus.mc.solaryeconomy.exception;

public class ConfigurationWriteException extends RuntimeException {

    public ConfigurationWriteException(String message) {
        super(message);
    }

    public ConfigurationWriteException(String message, Throwable cause) {
        super(message, cause);
    }

}
