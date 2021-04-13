package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

public class SubCmdReload extends SubCommand {

	public SubCmdReload(String command)
	{
		super("reload", "§cUse: /" + command + " reload", "solaryeconomy.commands.reload", "recarregar");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{

		SolaryEconomy.economia.saveAll();
		SolaryEconomy.economia.loadMoneyTop();
		SolaryEconomy.config.load();
		SolaryEconomy.messages.load();
		SolaryEconomy.refreshMoneyTop.load();

		sender.sendMessage("§eArquivos de configurações recarregados com sucesso.");

	}

}
