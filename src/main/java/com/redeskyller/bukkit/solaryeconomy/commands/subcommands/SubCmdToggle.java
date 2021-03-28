package com.redeskyller.bukkit.solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import com.redeskyller.bukkit.solaryeconomy.SolaryEconomy;
import com.redeskyller.bukkit.solaryeconomy.abstracts.SubCommand;

public class SubCmdToggle extends SubCommand {

	public SubCmdToggle(String command) {
		super("toggle", "§cUse: /" + command + " toggle", "solaryeconomy.commands.toggle");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String toggle = SolaryEconomy.economia.toggle(sender.getName()) ? "OFF" : "ON";
		sender.sendMessage(SolaryEconomy.mensagens.get("MONEY_TOGGLE").replace("{toggle}", toggle));

	}

}
