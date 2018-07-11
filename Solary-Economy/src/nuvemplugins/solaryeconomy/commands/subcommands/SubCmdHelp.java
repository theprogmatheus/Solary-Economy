package nuvemplugins.solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nuvemplugins.solaryeconomy.abstracts.SubCommand;

public class SubCmdHelp extends SubCommand {

	private String command;

	public SubCmdHelp(String command) {
		super("help", "§cUse: /" + command + " ajuda", "", "ajuda", "?");
		this.command = command;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(" ");

		if (sender instanceof Player) {
			sender.sendMessage("§a/" + command + " §8- §7ver seu saldo atual.");
			if (sender.hasPermission("solaryeconomy.commands.money"))
				sender.sendMessage("§a/" + command + " [jogador]§8- §7ver o saldo atual de um jogador.");

			if (sender.hasPermission("solaryeconomy.commands.pay"))
				sender.sendMessage("§a/" + command + " pay [jogador] [valor] §8- §7envia money a um jogador.");

			if (sender.hasPermission("solaryeconomy.commands.set"))
				sender.sendMessage("§a/" + command + " set [jogador] [valor] §8- §7seta o money de um jogador.");

			if (sender.hasPermission("solaryeconomy.commands.add"))
				sender.sendMessage("§a/" + command + " add [jogador] [valor] §8- §7adicionar money a um jogador.");

			if (sender.hasPermission("solaryeconomy.commands.remove"))
				sender.sendMessage("§a/" + command + " remove [jogador] [valor] §8- §7remove money de um jogador.");

			if (sender.hasPermission("solaryeconomy.commands.top"))
				sender.sendMessage("§a/" + command + " top §8- §7ver o money top do servidor.");

			sender.sendMessage("§a/" + command + " ajuda §8- §7ver os comandos do plugin.");

			if (sender.hasPermission("solaryeconomy.commands.toggle"))
				sender.sendMessage("§a/" + command + " toggle §8- §7habilitar/desabilitar o recebimento de coins.");

			if (sender.hasPermission("solaryeconomy.commands.criar"))
				sender.sendMessage("§a/" + command + " criar [nome] [valor] §8- §7criar uma conta.");

			if (sender.hasPermission("solaryeconomy.commands.deletar"))
				sender.sendMessage("§a/" + command + " deletar [nome] [valor] §8- §7deletar uma conta.");

			if (sender.hasPermission("solaryeconomy.commands.reload"))
				sender.sendMessage("§a/" + command + " reload §8- §7recarregar os arquivos de configs.");
			if (sender.hasPermission("solaryeconomy.commands.magnata"))
				sender.sendMessage("§a/" + command + " magnata §8- §7ver o magnata atual do servidor.");
		} else {
			sender.sendMessage("§a/" + command + " [jogador]§8- §7ver o saldo atual de um jogador.");
			sender.sendMessage("§a/" + command + " set [jogador] [valor] §8- §7seta o money de um jogador.");
			sender.sendMessage("§a/" + command + " add [jogador] [valor] §8- §7adicionar money a um jogador.");
			sender.sendMessage("§a/" + command + " remove [jogador] [valor] §8- §7remove money de um jogador.");
			sender.sendMessage("§a/" + command + " top §8- §7ver o money top do servidor.");
			sender.sendMessage("§a/" + command + " ajuda §8- §7ver os comandos do plugin.");
			sender.sendMessage("§a/" + command + " create [jogador] [valor] §8- §7criar uma conta.");
			sender.sendMessage("§a/" + command + " delete [jogador] [valor] §8- §7deletar uma conta.");
			sender.sendMessage("§a/" + command + " converter [plugin] §8- §7converter a economia de um plugin.");
			sender.sendMessage("§a/" + command + " reload §8- §7recarregar os arquivos de configs.");
			sender.sendMessage("§a/" + command + " magnata §8- §7ver o magnata atual do servidor.");
		}

		sender.sendMessage(" ");

	}

}
