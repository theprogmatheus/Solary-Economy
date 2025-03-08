package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import com.redeskyller.bukkit.solaryeconomy.RankAccount;
import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.hook.VaultChat;
@Deprecated
public class SubCmdMagnata extends SubCommand {

	public SubCmdMagnata(String command)
	{
		super("magnata", "§cUse: /" + command + " magnata", "solaryeconomy.commands.magnata");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{

		RankAccount account = SolaryEconomy.economia.getMagnata();
		sender.sendMessage(" ");
		if (account != null) {
			String accountname = account.getName();
			String valor = SolaryEconomy.numberFormat(account.getBalance());
			if (SolaryEconomy.config.getBoolean("economy_top.prefix")) {
				Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
				if (vault != null)
					accountname = VaultChat.getPrefix(account.getName()).concat(account.getName());
			}
			sender.sendMessage(SolaryEconomy.messages.get("MAGNATA_VIEW").replace("{player}", accountname)
					.replace("{valor}", valor));

		} else
			sender.sendMessage(SolaryEconomy.messages.get("MAGNATA_NOT_FOUND"));

		sender.sendMessage(" ");

	}

}
