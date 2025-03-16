package com.github.theprogmatheus.mc.solaryeconomy.config;

public class Env {

    /*
     * Database config
     */
    public static String DATABASE_TYPE;
    public static String DATABASE_ADDRESS;
    public static String DATABASE_NAME;
    public static String DATABASE_USERNAME;
    public static String DATABASE_PASSWORD;
    public static String DATABASE_TABLE_PREFIX;


    /*
     * Cache config
     */
    public static int CACHE_ACCOUNTS_MAX_SIZE;
    public static long CACHE_ACCOUNTS_EXPIRE;
    public static long CACHE_ACCOUNTS_AUTO_FLUSH_DELAY;

    /*
     * Lang config
     */
    public static String LANG;

    /*
     * Economy config
     */
    public static String ECONOMY_CURRENCY_SINGULAR;
    public static String ECONOMY_CURRENCY_PLURAL;
    public static int ECONOMY_CURRENCY_FORMATTER_PRECISION;
    public static boolean ECONOMY_CURRENCY_FORMATTER_WITH_TAG;
    public static double ECONOMY_START_VALUE;
    public static int ECONOMY_RANK_SIZE;
    public static long ECONOMY_RANK_UPDATE_DELAY;
    public static boolean ECONOMY_RANK_USE_PREFIX;
    public static int ECONOMY_RANK_MAX_NAME_SIZE;
    public static boolean ECONOMY_TYCOON_TAG;
    public static boolean ECONOMY_TYCOON_BROADCAST;

}
