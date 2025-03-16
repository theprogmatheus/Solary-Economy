package com.github.theprogmatheus.mc.solaryeconomy.hook;

import com.github.theprogmatheus.mc.solaryeconomy.config.Env;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
import java.util.List;

public class VaultEconomyHook extends AbstractEconomy {

    private final JavaPlugin plugin;
    private final EconomyService economyService;

    public VaultEconomyHook(JavaPlugin plugin, EconomyService economyService) {
        this.plugin = plugin;
        this.economyService = economyService;
    }

    @Override
    public boolean isEnabled() {
        return this.plugin.isEnabled();
    }

    @Override
    public String getName() {
        return this.plugin.getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 2;
    }

    @Override
    public String format(double v) {
        return "";
    }

    @Override
    public String currencyNamePlural() {
        return Env.ECONOMY_CURRENCY_PLURAL;
    }

    @Override
    public String currencyNameSingular() {
        return Env.ECONOMY_CURRENCY_SINGULAR;
    }

    @Override
    public boolean hasAccount(String accountId) {
        return this.economyService.getDefaultAccount(accountId.toLowerCase()) != null;
    }

    @Override
    public boolean hasAccount(String accountId, String world) {
        return this.hasAccount(accountId);
    }

    @Override
    public double getBalance(String accountId) {
        BankAccountEntity account = this.economyService.getDefaultAccount(accountId.toLowerCase());
        if (account == null) return 0;
        return account.getBalance().doubleValue();
    }

    @Override
    public double getBalance(String accountId, String world) {
        return getBalance(accountId);
    }

    @Override
    public boolean has(String accountId, double value) {
        return getBalance(accountId) >= value;
    }

    @Override
    public boolean has(String accountId, String world, double value) {
        return has(accountId, value);
    }

    @Override
    public EconomyResponse withdrawPlayer(String accountId, double value) {
        BankAccountEntity account = this.economyService.getDefaultAccount(accountId.toLowerCase());
        if (account == null)
            return new EconomyResponse(value, 0, EconomyResponse.ResponseType.FAILURE, "account not found");

        account.setBalance(account.getBalance().subtract(new BigDecimal(value)));

        return new EconomyResponse(value, account.getBalance().doubleValue(), EconomyResponse.ResponseType.SUCCESS, "withdraw success");
    }

    @Override
    public EconomyResponse withdrawPlayer(String accountId, String world, double value) {
        return withdrawPlayer(accountId, value);
    }

    @Override
    public EconomyResponse depositPlayer(String accountId, double value) {
        BankAccountEntity account = this.economyService.getDefaultAccount(accountId.toLowerCase());
        if (account == null)
            return new EconomyResponse(value, 0, EconomyResponse.ResponseType.FAILURE, "account not found");

        account.setBalance(account.getBalance().add(new BigDecimal(value)));

        return new EconomyResponse(value, account.getBalance().doubleValue(), EconomyResponse.ResponseType.SUCCESS, "deposit success");
    }

    @Override
    public EconomyResponse depositPlayer(String accountId, String world, double value) {
        return depositPlayer(accountId, value);
    }

    @Override
    public EconomyResponse createBank(String bankId, String world) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String bankId) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String bankId) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String bankId, double value) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String bankId, double value) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String bankId, double value) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String bankId, String world) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String bankId, String world) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String accountId) {
        return this.economyService.createDefaultAccountIfNotExists(accountId.toLowerCase(), accountId);
    }

    @Override
    public boolean createPlayerAccount(String accountId, String world) {
        return createPlayerAccount(accountId);
    }
}
