package nuvemplugins.solaryeconomy.plugin.vault;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.ServicePriority;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import nuvemplugins.solaryeconomy.app.SolaryEconomy;

public class VaultEconomy implements Economy {

	public VaultEconomy() {
		Bukkit.getServer().getServicesManager().register(Economy.class, this, SolaryEconomy.instance, ServicePriority.Highest);
	}

	@Override
	public boolean createPlayerAccount(String name) {
		return SolaryEconomy.economia.createAccount(name, 0);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		return createPlayerAccount(player.getName());
	}

	@Override
	public boolean createPlayerAccount(String name, String arg1) {
		return createPlayerAccount(name);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String arg1) {
		return createPlayerAccount(player.getName());
	}

	@Override
	public String currencyNamePlural() {
		return SolaryEconomy.config.getString("currency_name.plural");
	}

	@Override
	public String currencyNameSingular() {
		return SolaryEconomy.config.getString("currency_name.singular");
	}

	@Override
	public EconomyResponse depositPlayer(String name, double valor) {
		if (SolaryEconomy.economia.addMoney(name, valor)) {
			return new EconomyResponse(valor, getBalance(name), EconomyResponse.ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(valor, getBalance(name), EconomyResponse.ResponseType.FAILURE, "");
		}
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double valor) {
		return depositPlayer(player.getName(), valor);
	}

	@Override
	public EconomyResponse depositPlayer(String name, String arg1, double valor) {
		return depositPlayer(name, valor);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String arg1, double valor) {
		return depositPlayer(player.getName(), valor);
	}

	@Override
	public String format(double valor) {
		return SolaryEconomy.numberFormat(valor);
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	public double getBalance(String name) {
		return SolaryEconomy.economia.getMoney(name);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return getBalance(player.getName());
	}

	@Override
	public double getBalance(String name, String arg1) {
		return getBalance(name);
	}

	@Override
	public double getBalance(OfflinePlayer player, String arg1) {
		return getBalance(player.getName());
	}

	@Override
	public String getName() {
		return SolaryEconomy.PLUGIN_NAME;
	}

	@Override
	public boolean has(String name, double valor) {
		return (getBalance(name) >= valor);
	}

	@Override
	public boolean has(OfflinePlayer player, double valor) {
		return has(player.getName(), valor);
	}

	@Override
	public boolean has(String name, String arg1, double valor) {
		return has(name, valor);
	}

	@Override
	public boolean has(OfflinePlayer player, String arg1, double valor) {
		return has(player.getName(), valor);
	}

	@Override
	public boolean hasAccount(String name) {
		return SolaryEconomy.economia.hasAccount(name);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		return hasAccount(player.getName());
	}

	@Override
	public boolean hasAccount(String name, String arg1) {
		return hasAccount(name);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String arg1) {
		return hasAccount(player.getName());
	}

	@Override
	public EconomyResponse isBankMember(String arg0, String arg1) {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return SolaryEconomy.instance.isEnabled();
	}

	@Override
	public EconomyResponse withdrawPlayer(String name, double valor) {
		if (SolaryEconomy.economia.takeMoney(name, valor)) {
			return new EconomyResponse(valor, getBalance(name), EconomyResponse.ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(valor, getBalance(name), EconomyResponse.ResponseType.FAILURE, "");
		}
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double valor) {
		return withdrawPlayer(player.getName(), valor);
	}

	@Override
	public EconomyResponse withdrawPlayer(String name, String arg1, double valor) {
		return withdrawPlayer(name, valor);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String arg1, double valor) {
		return withdrawPlayer(player.getName(), valor);
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, String arg1) {
		return null;
	}

	@Override
	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
		return null;
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return null;
	}

	@Override
	public EconomyResponse bankDeposit(String arg0, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse bankHas(String arg0, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		return null;
	}

	@Override
	public EconomyResponse createBank(String arg0, String arg1) {
		return null;
	}

	@Override
	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		return null;
	}

	@Override
	public EconomyResponse deleteBank(String arg0) {
		return null;
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

}
