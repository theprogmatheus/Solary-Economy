package com.redeskyller.bukkit.solaryeconomy;

import java.math.BigDecimal;

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

	public String getName()
	{
		return name;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

}
