package com.redeskyller.bukkit.solaryeconomy.plugin.objetos;

import org.bukkit.Bukkit;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

public class RefreshMoneyTop implements Runnable {

	private int task;

	public RefreshMoneyTop() {
		this.task = -1;
		reload();
	}

	@Override
	public void run() {
		SolaryEconomy.economia.loadMoneyTop();
	}

	public void reload() {
		if (task != -1) {
			Bukkit.getScheduler().cancelTask(this.task);
		}
		int refresh_time = SolaryEconomy.config.getYaml().getInt("economy_top.refresh_time");
		this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(SolaryEconomy.instance, this, 10,
				20 * refresh_time);
	}

}
