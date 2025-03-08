package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import java.math.BigDecimal;

import org.bukkit.command.CommandSender;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
@Deprecated
public class SubCmdAdd extends SubCommand {

	public SubCmdAdd(String command)
	{
		super("add", "§cUse: /" + command + " add [jogador] [valor]", "solaryeconomy.commands.add", "give");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length >= 3) {

			String nome = args[1];

			BigDecimal valor = this.numbers.parseDecimal(args[2]);

			if (valor.doubleValue() <= 0) {
				sender.sendMessage(SolaryEconomy.messages.get("NUMBER_NULL"));
				return;
			}

			if (SolaryEconomy.economia.addBalance(nome, valor))
				sender.sendMessage(SolaryEconomy.messages.get("MONEY_ADD").replace("{player}", nome).replace("{valor}",
						SolaryEconomy.numberFormat(valor)));
			else
				sender.sendMessage(SolaryEconomy.messages.get("PLAYER_NOTFOUND").replace("{nome}", nome));

		} else
			sender.sendMessage(getUsage());

	}

}
