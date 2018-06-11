package nuvemplugins.solaryeconomy;

import java.util.List;

import nuvemplugins.solaryeconomy.app.SolaryEconomy;
import nuvemplugins.solaryeconomy.plugin.Economia;
import nuvemplugins.solaryeconomy.plugin.objetos.Account;

/**
 * Esta classe é para auxiliar na criação de novos plugins usando a api do
 * Solary-Economy
 * 
 */

public class API {

	public API() {
		this.economia = SolaryEconomy.economia;
	}

	private Economia economia;

	// Pega o magnata atual do servidor.
	public Account getMagnata() {
		return this.economia.getMagnata();
	}

	// Retorna true se a conta estiver com o coins desabilitado, ou false caso
	// contrário
	public boolean isToggle(String account) {
		return this.economia.isToggle(account);
	}

	// Retorna o money top atual do servidor.
	public List<Account> getMoneyTop() {
		return this.economia.getMoneyTop();
	}

	/*
	 * Você pode usar a classe 'Economia' para integrar qualquer plugin ao
	 * Solary-Economy *
	 */
}
