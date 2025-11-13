package com.redeskyller.bukkit.solaryeconomy;

import java.math.BigDecimal;

import lombok.Data;

@Deprecated
@Data
public class RankAccount {

	private final String name;
	private String displayName;
	private final BigDecimal balance;

	public RankAccount(final String nome, final BigDecimal balance)
	{
		this.name = nome;
		this.displayName = name;
		this.balance = balance;
	}
}
