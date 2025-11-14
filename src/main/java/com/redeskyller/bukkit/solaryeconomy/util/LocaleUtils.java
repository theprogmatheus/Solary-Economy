package com.redeskyller.bukkit.solaryeconomy.util;

import java.util.Locale;

public class LocaleUtils {

    public static Locale getLocaleByString(String localeString) {
        try {
            var localeStringParts = localeString.split("_");
            switch (localeStringParts.length) {
                case 1:
                    return new Locale(localeStringParts[0]);
                case 2:
                    return new Locale(localeStringParts[0], localeStringParts[1]);
                case 3:
                    return new Locale(localeStringParts[0], localeStringParts[1], localeStringParts[2]);
                default:
                    return null;
            }
        } catch (Exception ignored) {
            return null;
        }
    }
}
