package nuvemplugins.solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import nuvemplugins.solaryeconomy.abstracts.SubCommand;
import nuvemplugins.solaryeconomy.app.SolaryEconomy;

public class SubCmdRemove extends SubCommand {

	public SubCmdRemove(String command) {
		super("remove", "§cUse: /" + command + " remove [jogador] [valor]", "solaryeconomy.commands.remove", "take");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length >= 3) {
			String nome = args[1];
			double valor = this.numbers.parseDouble(args[2]);
			if (valor < 1) {
				sender.sendMessage(SolaryEconomy.mensagens.get("NUMBER_NULL"));
				return;
			}

			if (SolaryEconomy.economia.takeMoney(nome, valor)) {
				sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_REMOVE").replace("{player}", nome)
						.replace("{valor}", SolaryEconomy.numberFormat(valor)));
			} else {
				sender.sendMessage(SolaryEconomy.mensagens.get("PLAYER_NOTFOUND").replace("{nome}", nome));
			}

		} else {
			sender.sendMessage(getUsage());
		}

	}

}
