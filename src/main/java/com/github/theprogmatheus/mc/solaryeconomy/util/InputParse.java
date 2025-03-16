package com.github.theprogmatheus.mc.solaryeconomy.util;

import java.math.BigDecimal;

public class InputParse {


    public static String parseAccountId(String accountId) {
        return accountId.toLowerCase();
    }

    public static BigDecimal parseBigDecimal(String inputString) {
        return CurrencyFormatter.parseCurrency(inputString);
    }

}
