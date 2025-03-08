package com.redeskyller.bukkit.solaryeconomy.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
@Deprecated
public class CurrencyFormatter {

	private TreeMap<BigDecimal, String> dictionary;
	private Map<String, String> displays;

	public CurrencyFormatter(TreeMap<BigDecimal, String> dictionary, Map<String, String> displays) {
		this.dictionary = dictionary;
		this.displays = displays;
	}

	public String abbreviate(BigDecimal value) {
		if (value == null)
			return null;

		if (value.compareTo(BigDecimal.ONE) < 0)
			return value.toPlainString();

		Map.Entry<BigDecimal, String> entry = this.dictionary.floorEntry(value);
		if (entry == null)
			return value.toPlainString();

		BigDecimal divisor = entry.getKey();
		String suffix = entry.getValue();
		String display = this.displays.get(suffix);
		BigDecimal abbreviatedValue = value.divide(divisor, 2, BigDecimal.ROUND_DOWN);

		return abbreviatedValue.stripTrailingZeros().toPlainString()
				+ (display != null && !display.isBlank() ? display : suffix);
	}

	public BigDecimal unAbbreviate(String value) {
		if (value == null)
			return null;

		String numberPart = value.replaceAll("[a-zA-Z]", "").replace(",", ".");
		String suffix = value.replaceAll("[0-9.]", "").toLowerCase();

		BigDecimal number = new BigDecimal(numberPart);
		BigDecimal multiplier = this.dictionary.entrySet().stream().filter(e -> e.getValue().equals(suffix))
				.map(Map.Entry::getKey).findFirst().orElse(BigDecimal.ONE);

		return number.multiply(multiplier);
	}

	public BigDecimal parseInput(String input) {
		try {
			input = input.replace(",", ".");
			if (input.matches(".*[a-zA-Z].*")) {
				return unAbbreviate(input);
			} else {
				return new BigDecimal(input);
			}
		} catch (Exception e) {
			return null;
		}

	}
}
