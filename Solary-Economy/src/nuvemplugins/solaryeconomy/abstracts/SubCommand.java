package nuvemplugins.solaryeconomy.abstracts;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import nuvemplugins.solaryeconomy.util.Numbers;

public abstract class SubCommand {

	private String name;
	private String usage;
	private String permission;
	private List<String> alias;
	public Numbers numbers;

	public SubCommand(String name, String usage, String permission, String... alias) {
		this.name = name;
		this.usage = usage;
		this.permission = permission;
		this.alias = Arrays.asList(alias);
		this.numbers = new Numbers();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<String> getAlias() {
		return alias;
	}

	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

	public abstract void execute(CommandSender sender, String[] args);

}
