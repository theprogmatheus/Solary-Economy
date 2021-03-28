package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.abstracts.SubCommand;

public class SubCmdDeletar extends SubCommand {

	public SubCmdDeletar(String command)
	{
		super("deletar", "§cUse: /" + command + " deletar [nome]", "solaryeconomy.commands.deletar", "delete", "del");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length >= 2) {
			String nome = args[1];
			if (SolaryEconomy.economia.deleteAccount(nome))
				sender.sendMessage(SolaryEconomy.mensagens.get("ACCOUNT_DELETE").replace("{nome}", nome));
			else
				sender.sendMessage(SolaryEconomy.mensagens.get("ACCOUNT_NOFOUND").replace("{nome}", nome));
		} else
			sender.sendMessage(getUsage());

	}

}
