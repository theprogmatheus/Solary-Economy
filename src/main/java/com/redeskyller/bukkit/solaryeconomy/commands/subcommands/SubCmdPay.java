package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
@Deprecated
public class SubCmdPay extends SubCommand {

	public SubCmdPay(String command)
	{
		super("pay", "§cUse: /" + command + " pay [jogador] [valor]", "solaryeconomy.commands.pay", "pagar", "enviar");
	}

	@Override
	public void execute(CommandSender sender, String[] args)
	{
		if (args.length >= 3) {
			String nome = args[1];

			BigDecimal valor = this.numbers.parseDecimal(args[2]);

			if (valor.doubleValue() < 1.0) {
				sender.sendMessage(SolaryEconomy.messages.get("NUMBER_NULL"));
				return;
			}

			if (!(sender instanceof Player))
				return;

			if (sender.getName().equalsIgnoreCase(nome)) {
				sender.sendMessage(SolaryEconomy.messages.get("MONEY_PAY_ERRO"));
				return;
			}

			if (SolaryEconomy.economia.hasBalance(sender.getName(), valor)) {

				if (!SolaryEconomy.economia.isToggle(nome)) {
					if (SolaryEconomy.economia.addBalance(nome, valor)) {
						SolaryEconomy.economia.substractBalance(sender.getName(), valor);
						sender.sendMessage(SolaryEconomy.messages.get("MONEY_PAY_SENDER").replace("{player}", nome)
								.replace("{valor}", SolaryEconomy.numberFormat(valor)));

						Player target = Bukkit.getPlayer(nome);
						if (target != null)
							if (sender != target)
								target.sendMessage(SolaryEconomy.messages.get("MONEY_PAY_RECEIVER")
										.replace("{player}", sender.getName())
										.replace("{valor}", SolaryEconomy.numberFormat(valor)));

					} else
						sender.sendMessage(SolaryEconomy.messages.get("PLAYER_NOTFOUND").replace("{nome}", nome));
				} else
					sender.sendMessage(SolaryEconomy.messages.get("MONEY_TOGGLED"));
			} else
				sender.sendMessage(SolaryEconomy.messages.get("NO_MONEY"));

		} else
			sender.sendMessage(getUsage());

	}

}
