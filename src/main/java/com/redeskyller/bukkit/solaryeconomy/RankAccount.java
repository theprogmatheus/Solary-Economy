package com.redeskyller.bukkit.solaryeconomy;

import java.math.BigDecimal;

public class RankAccount {

	private final String name;
	private final BigDecimal balance;

	public RankAccount(final String nome, final BigDecimal balance)
	{
		this.name = nome;
		this.balance = balance;
	}

	public String getName()
	{
		return name;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

}
