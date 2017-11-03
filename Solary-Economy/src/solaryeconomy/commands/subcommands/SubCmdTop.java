package solaryeconomy.commands.subcommands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import solaryeconomy.SolaryEconomy;
import solaryeconomy.abstracts.SubCommand;
import solaryeconomy.plugin.objetos.Account;
import solaryeconomy.plugin.vault.Vault;

public class SubCmdTop extends SubCommand {

	public SubCmdTop(String command) {
		super("top", "§cUse: /" + command + " top", "solaryeconomy.commands.top", "rank");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		List<Account> moneytop = SolaryEconomy.economia.getMoneyTop();

		if (!moneytop.isEmpty()) {
			int i = 1;
			sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_TOP_TITLE"));
			sender.sendMessage(" ");
			for (Account account : moneytop) {
				String valor = SolaryEconomy.numberFormat(account.getValor());
				String accountname = account.getName();
				if (SolaryEconomy.config.getYaml().getBoolean("economy_top.prefix")) {
					Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
					if (vault != null) {
						accountname = Vault.getPrefix(account.getName()).concat(account.getName());
					}
				}
				sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_TOP_FORMAT").replace("{i}", "" + i)
						.replace("{player}", accountname).replace("{valor}", valor));
				i++;
			}
			sender.sendMessage(" ");

		} else {
			sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_TOP_TITLE"));
			sender.sendMessage(" ");
			sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_TOP_NULL"));
			sender.sendMessage(" ");
		}

	}

}
