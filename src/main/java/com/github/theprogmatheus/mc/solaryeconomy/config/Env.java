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
}
