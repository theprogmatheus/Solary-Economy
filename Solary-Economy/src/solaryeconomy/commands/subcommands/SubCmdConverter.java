package solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import solaryeconomy.SolaryEconomy;
import solaryeconomy.abstracts.SubCommand;
import solaryeconomy.threads.ConverterEssentials;

public class SubCmdConverter extends SubCommand {

	public SubCmdConverter(String command) {
		super("converter", "§cUse: /" + command + " converter [essentials]", "solaryeconomy.commands.converter",
				"convert");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length >= 2) {

			if (SolaryEconomy.converting != null) {
				sender.sendMessage("§cUma conversao ja esta em andamento.");
				return;
			} else {
				String plugin = args[1].toLowerCase();
				switch (plugin) {
				case "essentials":
					ConverterEssentials essentials = new ConverterEssentials(sender);
					essentials.start();
					sender.sendMessage("§aConversão de economia do Essentials iniciada com sucesso.");
					SolaryEconomy.converting = essentials;
					break;

				default:
					sender.sendMessage("§ceste plugin não suporta conversão de economia.");
					break;
				}

			}

		} else {
			sender.sendMessage(getUsage());
		}

	}

}
