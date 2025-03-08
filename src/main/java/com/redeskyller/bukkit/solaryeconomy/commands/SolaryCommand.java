package com.redeskyller.bukkit.solaryeconomy.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdAdd;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdCriar;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdDeletar;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdHelp;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdMagnata;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdPay;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdReload;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdRemove;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdSet;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdToggle;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCmdTop;
import com.redeskyller.bukkit.solaryeconomy.commands.subcommands.SubCommand;
@Deprecated
public class SolaryCommand implements CommandExecutor {

	private List<SubCommand> subcommands;

	public SolaryCommand(String command)
	{
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
		this.subcommands.add(new SubCmdMagnata(command));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender instanceof Player)
			if (!SolaryEconomy.economia.existsAccount(sender.getName()))
				SolaryEconomy.economia.createAccount(sender.getName(),
						new BigDecimal(SolaryEconomy.config.getDouble("start_value")));
		if (args.length >= 1) {
			String arg = args[0].toLowerCase();
			if (!this.subcommands.isEmpty())
				for (SubCommand subCommand : this.subcommands)
					if (arg.equalsIgnoreCase(subCommand.getName().toLowerCase())
							|| subCommand.getAlias().contains(arg)) {
						if (sender.hasPermission(subCommand.getPermission()) || subCommand.getPermission().isEmpty())
							subCommand.execute(sender, args);
						else
							sender.sendMessage(SolaryEconomy.messages.get("NO_PERMISSION"));
						return false;
					}

			if (sender.hasPermission("solaryeconomy.commands.money.other"))
				if (SolaryEconomy.economia.existsAccount(args[0])) {
					if (sender.getName().equals(args[0])) {
						if (sender instanceof Player)
							sender.sendMessage(SolaryEconomy.messages.get("MONEY").replace("{valor}",
									SolaryEconomy.numberFormat(SolaryEconomy.economia.getBalance(sender.getName()))));
						else
							sender.sendMessage("§a/" + command.getName() + " ajuda §8- §7ver os comandos do plugin.");
					} else
						sender.sendMessage(SolaryEconomy.messages.get("MONEY_OTHER")
								.replace("{valor}",
										SolaryEconomy.numberFormat(SolaryEconomy.economia.getBalance(args[0])))
								.replace("{player}", args[0]));
				} else
					sender.sendMessage(SolaryEconomy.messages.get("PLAYER_NOTFOUND").replace("{player}", args[0]));
		} else if (sender instanceof Player)
			sender.sendMessage(SolaryEconomy.messages.get("MONEY").replace("{valor}",
					SolaryEconomy.numberFormat(SolaryEconomy.economia.getBalance(sender.getName()))));
		else
			sender.sendMessage("§a/" + command.getName() + " ajuda §8- §7ver os comandos do plugin.");

		return false;
	}

}
