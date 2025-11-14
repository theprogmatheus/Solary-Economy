package com.redeskyller.bukkit.solaryeconomy.config.env;

import com.redeskyller.bukkit.solaryeconomy.config.Configuration;
import com.redeskyller.bukkit.solaryeconomy.config.ConfigurationHolder;

/**
 * Here your set all config values from auto map
 */

@Configuration("config.yml")
public class Config {


    public static final ConfigurationHolder<String> CONFIG_VERSION
            = new ConfigurationHolder<>("config-version", String.class);

    public static final ConfigurationHolder<String> LANG_DEFAULT
            = new ConfigurationHolder<>("lang.default", String.class);
    public static final ConfigurationHolder<Boolean> LANG_INDIVIDUAL
            = new ConfigurationHolder<>("lang.individual", Boolean.class);

}