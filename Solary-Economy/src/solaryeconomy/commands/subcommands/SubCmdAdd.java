package solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import solaryeconomy.SolaryEconomy;
import solaryeconomy.abstracts.SubCommand;

public class SubCmdAdd extends SubCommand {

	public SubCmdAdd(String command) {
		super("add", "§cUse: /" + command + " add [jogador] [valor]", "solaryeconomy.commands.add", "give");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length >= 3) {
			String nome = args[1];
			double valor = -1;
			try {
				valor = Double.parseDouble(args[2]);
			} catch (Exception e) {
				sender.sendMessage(SolaryEconomy.mensagens.get("NUMBER_NULL"));
				return;
			}

			if (valor < 1) {
				sender.sendMessage(SolaryEconomy.mensagens.get("NUMBER_NULL"));
				return;
			}

			if (SolaryEconomy.economia.addMoney(nome, valor)) {
				sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_ADD").replace("{player}", nome).replace("{valor}",
						SolaryEconomy.numberFormat(valor)));
			} else {
				sender.sendMessage(SolaryEconomy.mensagens.get("PLAYER_NOTFOUND").replace("{nome}", nome));
			}

		} else {
			sender.sendMessage(getUsage());
		}

	}

}
