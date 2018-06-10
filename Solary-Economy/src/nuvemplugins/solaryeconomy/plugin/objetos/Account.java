package nuvemplugins.solaryeconomy.plugin.objetos;

public class Account {
	public Account(String nome, double valor) {
		this.name = nome;
		this.valor = valor;
	}

	private String name;
	private double valor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
