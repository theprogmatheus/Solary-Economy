package solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import solaryeconomy.SolaryEconomy;
import solaryeconomy.abstracts.SubCommand;

public class SubCmdCriar extends SubCommand {

	public SubCmdCriar(String command) {
		super("criar", "§cUse: /" + command + " criar [nome] [valor]", "solaryeconomy.commands.criar", "create", "new");
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

			if (SolaryEconomy.economia.createAccount(nome, valor)) {
				sender.sendMessage(SolaryEconomy.mensagens.get("ACCOUNT_CREATE").replace("{nome}", nome));

			} else {
				sender.sendMessage(SolaryEconomy.mensagens.get("ACCOUNT_EXISTS").replace("{nome}", nome));
			}

		} else {
			sender.sendMessage(getUsage());
		}

	}

}
