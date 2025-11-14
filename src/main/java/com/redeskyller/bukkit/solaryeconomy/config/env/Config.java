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

    public static final ConfigurationHolder<String> DATABASE_TYPE
            = new ConfigurationHolder<>("database.type", String.class);

    public static final ConfigurationHolder<String> DATABASE_MYSQL_HOST
            = new ConfigurationHolder<>("database.mysql.host", String.class);

    public static final ConfigurationHolder<Integer> DATABASE_MYSQL_PORT
            = new ConfigurationHolder<>("database.mysql.port", Integer.class);

    public static final ConfigurationHolder<String> DATABASE_MYSQL_DATABASE
            = new ConfigurationHolder<>("database.mysql.database", String.class);

    public static final ConfigurationHolder<String> DATABASE_MYSQL_USERNAME
            = new ConfigurationHolder<>("database.mysql.username", String.class);

    public static final ConfigurationHolder<String> DATABASE_MYSQL_PASSWORD
            = new ConfigurationHolder<>("database.mysql.password", String.class);

    public static final ConfigurationHolder<String> DATABASE_MYSQL_TABLE_PREFIX
            = new ConfigurationHolder<>("database.mysql.table-prefix", String.class);

    public static final ConfigurationHolder<Boolean> DATABASE_MYSQL_MARIADB
            = new ConfigurationHolder<>("database.mysql.table-prefix", Boolean.class);
}