package com.github.theprogmatheus.mc.solaryeconomy.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyFormatter {

    public static String formatCurrency(BigDecimal amount, int precision) {
        CurrencyUnit[] values = CurrencyUnit.values();
        for (int i = values.length; i > 0; i--) {
            CurrencyUnit unit = values[i - 1];

            if (amount.compareTo(unit.value) >= 0) {
                BigDecimal result = amount.divide(unit.value, precision, RoundingMode.FLOOR);
                return result.toPlainString() + unit.getDisplayTag();
            }
        }

        return amount.toPlainString();
    }

    public static BigDecimal parseCurrency(String input) {
        try {
            return new BigDecimal(input.replace(",", "."));
        } catch (Exception e) {
            return parseCurrencyWithTag(input);
        }
    }

    public static BigDecimal parseCurrencyWithTag(String input) {
        try {
            input = input.replace(",", ".");
            String numberPart = input.replaceAll("[a-zA-Z]", "");
            String suffix = input.replaceAll("[0-9.]", "").toLowerCase();
            BigDecimal number = new BigDecimal(numberPart);
            BigDecimal multiplier = CurrencyUnit.fromTag(suffix).getValue();
            return number.multiply(multiplier);
        } catch (Exception e) {
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CurrencyUnit {

        THOUSAND(new BigDecimal("1e3"), "k", "K"),
        MILLION(new BigDecimal("1e6"), "m", "M"),
        BILLION(new BigDecimal("1e9"), "b", "B"),
        TRILLION(new BigDecimal("1e12"), "t", "T"),
        QUADRILLION(new BigDecimal("1e15"), "qa", "Qa"),
        QUINTILLION(new BigDecimal("1e18"), "qi", "Qi"),
        SEXTILLION(new BigDecimal("1e21"), "sx", "Sx"),
        SEPTILLION(new BigDecimal("1e24"), "sp", "Sp"),
        OCTILLION(new BigDecimal("1e27"), "oc", "Oc"),
        NONILLION(new BigDecimal("1e30"), "no", "No"),
        DECILLION(new BigDecimal("1e33"), "de", "De");

        private final BigDecimal value;
        private final String tag;
        private final String displayTag;

        public static CurrencyUnit fromTag(String tag) {
            for (CurrencyUnit unit : values()) {
                if (unit.tag.equals(tag.toLowerCase()))
                    return unit;
            }
            throw new IllegalArgumentException("Unrecognized tag: " + tag);
        }
    }
}