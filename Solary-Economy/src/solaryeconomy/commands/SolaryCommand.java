package solaryeconomy.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import solaryeconomy.SolaryEconomy;
import solaryeconomy.abstracts.AbstractCommand;
import solaryeconomy.abstracts.SubCommand;
import solaryeconomy.commands.subcommands.SubCmdAdd;
import solaryeconomy.commands.subcommands.SubCmdConverter;
import solaryeconomy.commands.subcommands.SubCmdCriar;
import solaryeconomy.commands.subcommands.SubCmdDeletar;
import solaryeconomy.commands.subcommands.SubCmdHelp;
import solaryeconomy.commands.subcommands.SubCmdMagnata;
import solaryeconomy.commands.subcommands.SubCmdPay;
import solaryeconomy.commands.subcommands.SubCmdReload;
import solaryeconomy.commands.subcommands.SubCmdRemove;
import solaryeconomy.commands.subcommands.SubCmdSet;
import solaryeconomy.commands.subcommands.SubCmdToggle;
import solaryeconomy.commands.subcommands.SubCmdTop;

public class SolaryCommand extends AbstractCommand {

	private List<SubCommand> subcommands;

	public SolaryCommand(String pluginname, String command, String... aliases) {
		super(command, "/" + command + " [args...]", "comando usado no plugin " + pluginname, Arrays.asList(aliases));
		this.subcommands = new ArrayList<SubCommand>();
		this.subcommands.add(new SubCmdHelp(command));
		this.subcommands.add(new SubCmdTop(command));
		this.subcommands.add(new SubCmdCriar(command));
		this.subcommands.add(new SubCmdDeletar(command));
		this.subcommands.add(new SubCmdAdd(command));
		this.subcommands.add(new SubCmdRemove(command));
		this.subcommands.add(new SubCmdSet(command));
		this.subcommands.add(new SubCmdPay(command));
		this.subcommands.add(new SubCmdToggle(command));
		this.subcommands.add(new SubCmdReload(command));
		this.subcommands.add(new SubCmdConverter(command));
		this.subcommands.add(new SubCmdMagnata(command));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (!SolaryEconomy.economia.hasAccount(sender.getName())) {
				SolaryEconomy.economia.createAccount(sender.getName(),
						SolaryEconomy.config.getYaml().getDouble("start_value"));
			}
		}
		if (args.length >= 1) {
			String arg = args[0].toLowerCase();
			if (!this.subcommands.isEmpty()) {
				for (SubCommand subCommand : this.subcommands) {
					if (arg.equalsIgnoreCase(subCommand.getName().toLowerCase())
							|| subCommand.getAlias().contains(arg)) {
						if (sender.hasPermission(subCommand.getPermission()) || subCommand.getPermission().isEmpty()) {
							subCommand.execute(sender, args);
						} else {
							sender.sendMessage(SolaryEconomy.mensagens.get("NO_PERMISSION"));
						}
						return false;
					}
				}
			}

			if (sender.hasPermission("solaryeconomy.commands.money.other")) {
				if (SolaryEconomy.economia.hasAccount(args[0])) {
					if (sender.getName().equals(args[0])) {
						if (sender instanceof Player) {
							sender.sendMessage(SolaryEconomy.mensagens.get("MONEY").replace("{valor}",
									SolaryEconomy.numberFormat(SolaryEconomy.economia.getMoney(sender.getName()))));
						} else {
							sender.sendMessage("§a/" + command.getName() + " ajuda §8- §7ver os comandos do plugin.");
						}
					} else {
						sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_OTHER")
								.replace("{valor}",
										SolaryEconomy.numberFormat(SolaryEconomy.economia.getMoney(args[0])))
								.replace("{player}", args[0]));
					}
				} else {
					sender.sendMessage(SolaryEconomy.mensagens.get("PLAYER_NOTFOUND").replace("{player}", args[0]));
				}

			}
		} else {
			if (sender instanceof Player) {
				sender.sendMessage(SolaryEconomy.mensagens.get("MONEY").replace("{valor}",
						SolaryEconomy.numberFormat(SolaryEconomy.economia.getMoney(sender.getName()))));
			} else {
				sender.sendMessage("§a/" + command.getName() + " ajuda §8- §7ver os comandos do plugin.");
			}
		}

		return false;
	}

}
