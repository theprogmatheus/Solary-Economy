package nuvemplugins.solaryeconomy.util;

public class Cache {

	private String account;
	private double money;
	private long lastUpdate;
	private long updateTime;

	public Cache(String account, double money, long updateTime) {
		this.account = account;
		this.money = money;
		this.lastUpdate = System.currentTimeMillis();
		this.updateTime = updateTime;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isCanRefresh() {
		long delay = (this.updateTime * 1000);
		long nextupdate = (this.lastUpdate + delay);
		return (System.currentTimeMillis() >= nextupdate);
	}
}
