package com.redeskyller.bukkit.solaryeconomy.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

public class NumbersUtils {

	public static final String[] SUFFIXS = new String[] { "", "K", "M", "B", "T", "Q", "QQ", "S", "SS", "OC", "N", "D",
			"UN", "DD", "TR", "QT", "QN" };

	public BigDecimal parseDecimal(String numberString) {
		try {
			if (SolaryEconomy.currencyFormatter != null
					&& SolaryEconomy.config.getBoolean("abbreviations.enable.commands")) {

				return SolaryEconomy.currencyFormatter.parseInput(numberString);
			} else
				return new BigDecimal(numberString);
		} catch (Exception exception) {
			return new BigDecimal(-1.0);
		}
	}

	public static String formatSimple(double value) {
		int index = 0;
		while (value / 1000.0D >= 1.0D) {
			value /= 1000.0D;
			index++;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		return String.format("%s%s", new Object[] { decimalFormat.format(value), SUFFIXS[index] }).replace(",", ".");
	}
}
