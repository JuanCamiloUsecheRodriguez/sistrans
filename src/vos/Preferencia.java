package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Preferencia {

	@JsonProperty(value="cliente")
	private int cliente;
	
	@JsonProperty(value="categoria")
	private int categoria;

	public Preferencia(@JsonProperty(value="cliente")int cliente, @JsonProperty(value="categoria")int categoria) {
		super();
		this.cliente = cliente;
		this.categoria = categoria;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	
	
	
	
}
