package nuvemplugins.solaryeconomy.threads;

import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nuvemplugins.solaryeconomy.app.SolaryEconomy;

public class ConverterIconomy extends Thread implements Runnable {
	private CommandSender sender;
	private int time;

	public ConverterIconomy(CommandSender sender) {
		this.sender = sender;
	}

	public int getTime() {
		return this.time;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		try {
			Class<?> Queried = Class.forName("com.iCo6.system.Queried");
			Method accounts = Queried.getDeclaredMethod("accountList", new Class[0]);
			accounts.setAccessible(true);
			List<String> lista = (List<String>) accounts.invoke(null, new Object[0]);
			for (int i = lista.size() - 1; i >= 0; i--) {
				String account = (String) lista.get(i);
				Method balanceM = Queried.getDeclaredMethod("getBalance", new Class[] { String.class });
				balanceM.setAccessible(true);
				Double balance = (Double) balanceM.invoke(null, account);
				if (balance.doubleValue() > 0) {
					if (SolaryEconomy.economia.hasAccount(account)) {
						SolaryEconomy.economia.setMoney(account, balance.doubleValue());
					} else {
						SolaryEconomy.economia.createAccount(account, balance.doubleValue());
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
						this.sender.sendMessage(
								"§aConversão de economia do Iconomy concluído com sucesso.");
					}
					Bukkit.getConsoleSender().sendMessage("§a[Solary-Economy] §eConversao de economia do Iconomy concluido com sucesso.");
				} else {
					sleep(200L);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}