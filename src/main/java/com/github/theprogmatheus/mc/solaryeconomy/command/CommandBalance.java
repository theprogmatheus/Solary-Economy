package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.config.Lang;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import com.github.theprogmatheus.mc.solaryeconomy.service.EconomyService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class CommandBalance extends AbstractCommand {

    private final EconomyService economyService;

    public CommandBalance() {
        super(new String[]{"balance", "bal", "money"}, "Check players balance", "solaryeconomy.command.balance", "balance <player>");
        this.economyService = SolaryEconomy.getInstance().getEconomyService();
        setChildren(new AbstractCommand[]{
                new CommandSet(),
                new CommandAdd(),
                new CommandRemove(),
                new CommandPay(),
                new CommandRank()
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0 && (sender instanceof Player)) {
            BankAccountEntity account = this.economyService.getDefaultAccountOrCreateIfNotExists((Player) sender);
            sender.sendMessage(MessageFormat.format(Lang.CURRENT_SELF_BALANCE, this.economyService.formatBigDecimal(account.getBalance())));
            return true;
        }

        if (args.length == 1 && checkPermission(sender, "other")) {
            BankAccountEntity account = this.economyService.getDefaultAccount(args[0].toLowerCase());
            if (account != null) {
                sender.sendMessage(MessageFormat.format(Lang.CURRENT_PLAYER_BALANCE, this.economyService.formatBigDecimal(account.getBalance()), account.getOwnerName()));
            } else {
                sender.sendMessage(MessageFormat.format(Lang.ACCOUNT_NOT_FOUND, args[0]));
            }
            return true;
        }


        return false;
    }
}
