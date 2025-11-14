package com.redeskyller.bukkit.solaryeconomy.config.lang;

import com.redeskyller.bukkit.solaryeconomy.config.ConfigurationFile;
import com.redeskyller.bukkit.solaryeconomy.util.ArrayUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.redeskyller.bukkit.solaryeconomy.config.ConfigurationManager.LOG_FORMAT;
import static com.redeskyller.bukkit.solaryeconomy.util.LocaleUtils.getLocaleByString;


@RequiredArgsConstructor
@Getter
@Setter
public class MessageManager {

    private final Logger logger;
    private final File folder;
    private final String resourcePath;
    private final Locale defaultLocale;
    private final Map<String, MessageFile> langs = new ConcurrentHashMap<>();
    private boolean individualLang = true;

    public void loadLanguages() {
        for (var locale : Locale.getAvailableLocales()) {
            var lang = normalizedTag(locale);
            try {
                var file = new File(folder, String.format("%s.yml", lang));
                var messageFile = new MessageFile(logger, file, String.format("%s/%s.yml", resourcePath, lang));
                if (!messageFile.existsDefaultResource())
                    continue;

                langs.put(lang, (MessageFile) messageFile.createIfNotExistsAndLoad());

                log(String.format("Message lang loaded successfully: %s (%s) from %s", lang, locale.getDisplayLanguage(Locale.ENGLISH), messageFile.getResourceName()));
            } catch (Exception e) {
                log(String.format("Failed to load message lang: %s. Cause: %s", lang, e.getMessage()));
            }
        }
        if (getDefaultMessageFile() == null)
            log("The default message lang cant be null.", new NullPointerException("getDefaultMessageFile() is null"));
    }

    /**
     * You can change this if you need
     */
    public Locale getPlayerLocale(Player player) {
        return getPlayerLocaleByClient(player);
    }

    private Locale getPlayerLocaleByClient(Player player) {
        var locale = getLocaleByString(player.getLocale());
        return locale == null ? getDefaultLocale() : locale;
    }

    public MessageFile getMessageFile(Player player) {
        if (!individualLang)
            return getDefaultMessageFile();
        return getMessageFile(getPlayerLocale(player));
    }

    public MessageFile getMessageFile(Locale locale) {
        var key = normalizedTag(locale);

        if (key.isEmpty())
            return getDefaultMessageFile();

        var messageFile = this.langs.get(key);
        if (messageFile != null)
            return messageFile;

        var lang = locale.getLanguage().toLowerCase();

        messageFile = langs.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(String.format("%s_", lang)))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);

        return messageFile != null ? messageFile : getDefaultMessageFile();
    }

    public MessageFile getDefaultMessageFile() {
        return this.langs.get(normalizedTag(this.defaultLocale));
    }

    public void reload(Locale locale) {
        var config = langs.get(normalizedTag(locale));
        if (config != null)
            config.load();
    }

    public void reloadAll() {
        langs.values().forEach(ConfigurationFile::load);
    }


    public void sendMessage(CommandSender sender, MessageKey messageKey) {
        sendMessage(sender, messageKey, new String[0]);
    }

    public void sendMessage(CommandSender sender, MessageKey messageKey, String... arrayPlaceholders) {
        sendMessage(sender, messageKey, ArrayUtils.toMap(arrayPlaceholders));
    }

    public void sendMessage(CommandSender sender, MessageKey messageKey, Map<String, String> placeholders) {
        var messageFile = this.getDefaultMessageFile();
        if (sender instanceof Player)
            messageFile = this.getMessageFile((Player) sender);

        List<String> message = null;
        if (messageKey.isList())
            message = messageFile.getMessageList(messageKey, placeholders);
        else {
            var rawMessage = messageFile.getMessage(messageKey, placeholders);
            if (rawMessage != null)
                message = List.of(rawMessage);
        }

        if (message != null)
            message.forEach(sender::sendMessage);
    }

    private String normalizedTag(Locale locale) {
        if (locale == null) return "";
        return locale.toString().toLowerCase();
    }

    private void log(String message) {
        log(message, null);
    }

    private void log(String message, Throwable cause) {
        if (cause == null)
            this.logger.info(String.format(LOG_FORMAT, message));
        else {
            this.logger.severe(String.format(LOG_FORMAT, message));
            Stream.of(cause.getStackTrace()).map(StackTraceElement::toString).forEach(line -> this.logger.severe("  at ".concat(line)));
        }
    }

}