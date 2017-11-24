package solaryeconomy.commands.subcommands;

import org.bukkit.command.CommandSender;

import solaryeconomy.SolaryEconomy;
import solaryeconomy.abstracts.SubCommand;
import solaryeconomy.manager.Mensagens;
import solaryeconomy.util.Config;

public class SubCmdReload extends SubCommand {

	public SubCmdReload(String command) {
		super("reload", "§cUse: /" + command + " reload", "solaryeconomy.commands.reload", "recarregar");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		SolaryEconomy.economia.loadMoneyTop();
		SolaryEconomy.config = new Config(SolaryEconomy.instance, "config.yml");
		SolaryEconomy.mensagens = new Mensagens(SolaryEconomy.instance);
		SolaryEconomy.refreshMoneyTop.reload();
		sender.sendMessage("§aArquivos de configurações recarregados com sucesso.");

	}

}
