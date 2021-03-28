package com.redeskyller.bukkit.solaryeconomy.util;

import java.math.BigDecimal;

public class NumbersUtils {

	public BigDecimal parseDecimal(String numberString)
	{
		try {
			return new BigDecimal(numberString);
		} catch (Exception exception) {
			return new BigDecimal(-1.0);
		}
	}
}
