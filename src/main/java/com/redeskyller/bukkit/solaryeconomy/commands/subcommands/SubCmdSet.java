package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import java.math.BigDecimal;

import org.bukkit.command.CommandSender;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;

public class SubCmdSet extends SubCommand {

	public SubCmdSet(String command)
	{
		super("set", "§cUse: /" + command + " set [jogador] [valor]", "solaryeconomy.commands.set", "definir", "setar");
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

			if (SolaryEconomy.economia.setBalance(nome, valor))
				sender.sendMessage(SolaryEconomy.messages.get("MONEY_SET").replace("{player}", nome).replace("{valor}",
						SolaryEconomy.numberFormat(valor)));
			else
				sender.sendMessage(SolaryEconomy.messages.get("PLAYER_NOTFOUND").replace("{nome}", nome));

		} else
			sender.sendMessage(getUsage());

	}

}
