package com.redeskyller.bukkit.solaryeconomy.config.lang;

import com.redeskyller.bukkit.solaryeconomy.config.ConfigurationFile;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MessageFile extends ConfigurationFile {

    public MessageFile(Logger logger, File file, String resourcePath) {
        super(logger, file, resourcePath);
    }

    public String getMessage(MessageKey messageKey) {
        return getMessage(messageKey, null);
    }

    public String getMessage(MessageKey messageKey, Map<String, String> placeholders) {
        if (messageKey == null)
            return null;
        return applyStringModifications(getString(messageKey.getPath()), placeholders);
    }

    public List<String> getMessageList(MessageKey messageKey) {
        return getMessageList(messageKey, null);
    }

    public List<String> getMessageList(MessageKey messageKey, Map<String, String> placeholders) {
        if (messageKey == null)
            return null;

        return getStringList(messageKey.getPath()).stream().map(string -> applyStringModifications(string, placeholders)).toList();
    }

    private String applyStringModifications(String string, Map<String, String> placeholders) {
        if (string == null || string.isBlank())
            return string;

        if (placeholders != null)
            for (var entry : placeholders.entrySet())
                string = string.replace("{%s}".formatted(entry.getKey()), entry.getValue());

        return ChatColor.translateAlternateColorCodes('&', string);
    }

}