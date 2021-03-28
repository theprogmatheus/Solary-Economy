package com.redeskyller.bukkit.solaryeconomy.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

public class RefreshMoneyTop {

	private BukkitRunnable bukkitRunnable;

	public RefreshMoneyTop load()
	{

		if (this.bukkitRunnable != null)
			this.bukkitRunnable.cancel();

		this.bukkitRunnable = new BukkitRunnable() {
			@Override
			public void run()
			{
				SolaryEconomy.economia.loadMoneyTop();
			}
		};

		long refreshTime = (SolaryEconomy.config.getLong("economy_top.refresh_time") * 20);

		this.bukkitRunnable.runTaskTimerAsynchronously(SolaryEconomy.getInstance(), refreshTime, refreshTime);

		return this;
	}

}
