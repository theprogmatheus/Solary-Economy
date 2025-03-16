package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import com.github.theprogmatheus.mc.solaryeconomy.config.Lang;
import com.github.theprogmatheus.mc.solaryeconomy.database.entity.BankAccountEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;
import java.util.List;

public class CommandRank extends AbstractCommand {

    protected CommandRank() {
        super(new String[]{"rank", "top"}, "Ranking of the richest players", "solaryeconomy.command.rank", "rank");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<BankAccountEntity> rank = SolaryEconomy.getInstance().getEconomyService().getAccountCache().getAccountsRank();

        if (Lang.COMMAND_RANK_FORMAT_HEADER != null)
            sender.sendMessage(MessageFormat.format(Lang.COMMAND_RANK_FORMAT_HEADER, rank.size()));
        for (int i = 0; i < rank.size(); i++) {
            BankAccountEntity account = rank.get(i);
            sender.sendMessage(MessageFormat.format(Lang.COMMAND_RANK_FORMAT_BODY, (i + 1), account.getOwnerName(), account.getBalance().toPlainString()));
        }
        if (Lang.COMMAND_RANK_FORMAT_FOOTER != null)
            sender.sendMessage(MessageFormat.format(Lang.COMMAND_RANK_FORMAT_FOOTER, rank.size()));
        return true;
    }
}
