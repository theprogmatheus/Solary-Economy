package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.abstracts.SubCommand;
import com.redeskyller.bukkit.solaryeconomy.manager.Mensagens;
import com.redeskyller.bukkit.solaryeconomy.util.Config;

public class SubCmdReload extends SubCommand {

	public SubCmdReload(String command)
	{
		super("reload", "§cUse: /" + command + " reload", "solaryeconomy.commands.reload", "recarregar");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{

		SolaryEconomy.economia.loadMoneyTop();
		SolaryEconomy.config = new Config(SolaryEconomy.getInstance(), "config.yml");
		SolaryEconomy.mensagens = new Mensagens(SolaryEconomy.getInstance());
		SolaryEconomy.refreshMoneyTop.reload();

		sender.sendMessage("§eArquivos de configurações recarregados com sucesso.");

	}

}
