package solaryeconomy.threads;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import solaryeconomy.SolaryEconomy;

public class ConverterEssentials extends Thread implements Runnable {
	private CommandSender sender;
	private int time;

	public ConverterEssentials(CommandSender sender) {
		this.sender = sender;
	}

	public int getTime() {
		return this.time;
	}

	public void run() {
		try {
			File directory = new File(Bukkit.getServer().getPluginManager().getPlugin("Essentials").getDataFolder(),
					"userdata");
			directory.mkdirs();
			File[] files = directory.listFiles();
			for (int i = files.length - 1; i >= 0; i--) {
				if (!files[i].isDirectory()) {
					YamlConfiguration playerdata = YamlConfiguration.loadConfiguration(files[i]);
					if ((playerdata.contains("lastAccountName")) && (playerdata.contains("money"))) {
						String account = playerdata.getString("lastAccountName");
						double money = Double.parseDouble(playerdata.getString("money"));
						if (money > 0) {
							if (SolaryEconomy.economia.hasAccount(account)) {
								SolaryEconomy.economia.setMoney(account, money);
							} else {
								SolaryEconomy.economia.createAccount(account, money);
							}

						}
						this.time = (i / 5);
						Bukkit.getConsoleSender().sendMessage("§a[Solary-Economy] §eConvertendo conta: " + account);

						if (i % 10 == 0) {
							Bukkit.getConsoleSender().sendMessage("§a[Solary-Economy] §eTempo restante: " + i / 5 + " "
									+ (i / 5 > 1 ? "segundos" : "segundo"));
						}
						if (i == 0) {
							SolaryEconomy.converting = null;
							interrupt();
							if (((this.sender instanceof Player)) && (((Player) this.sender).isOnline())) {
								this.sender.sendMessage("§aConversão de economia do Essentials concluído com sucesso.");
							}
							Bukkit.getConsoleSender().sendMessage("§a[Solary-Economy] §eConversao de economia do Essentials concluido com sucesso.");
						} else {
							sleep(200L);
						}
					}
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}