package com.github.theprogmatheus.mc.solaryeconomy.command;

import com.github.theprogmatheus.mc.solaryeconomy.SolaryEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandReload extends AbstractCommand {

    protected CommandReload() {
        super(new String[]{"reload", "rl"}, "Reload plugin configurations", "solaryeconomy.command.reload", "reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        SolaryEconomy.getInstance().getConfigurationService().startup();
        sender.sendMessage("§aArquivos de configurações recarregados com sucesso.");

        return true;
    }
}
