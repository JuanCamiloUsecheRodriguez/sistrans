package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class NotaDebito {

	@JsonProperty(value="cliente")
	private int cliente;

	@JsonProperty(value="valor")
	private int valor;

	@JsonProperty(value="reclamada")
	private boolean reclamada;

	public NotaDebito(@JsonProperty(value="cliente")int cliente,
					@JsonProperty(value="valor")int valor,
					@JsonProperty(value="reclamada")boolean reclamada) {
		super();
		this.cliente = cliente;
		this.valor = valor;
		this.reclamada = reclamada;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isReclamada() {
		return reclamada;
	}

	public void setReclamada(boolean reclamada) {
		this.reclamada = reclamada;
	}





}
