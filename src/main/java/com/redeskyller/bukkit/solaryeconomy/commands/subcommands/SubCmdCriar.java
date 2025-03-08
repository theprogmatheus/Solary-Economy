package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import java.math.BigDecimal;

import org.bukkit.command.CommandSender;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
@Deprecated
public class SubCmdCriar extends SubCommand {

	public SubCmdCriar(String command)
	{
		super("criar", "§cUse: /" + command + " criar [nome] [valor]", "solaryeconomy.commands.criar", "create", "new");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length >= 3) {
			String nome = args[1];

			BigDecimal valor = this.numbers.parseDecimal(args[2]);

			if (valor.doubleValue() < 0) {
				sender.sendMessage(SolaryEconomy.messages.get("NUMBER_NULL"));
				return;
			}

			if (SolaryEconomy.economia.createAccount(nome, valor))
				sender.sendMessage(SolaryEconomy.messages.get("ACCOUNT_CREATE").replace("{nome}", nome));
			else
				sender.sendMessage(SolaryEconomy.messages.get("ACCOUNT_EXISTS").replace("{nome}", nome));

		} else
			sender.sendMessage(getUsage());

	}

}
