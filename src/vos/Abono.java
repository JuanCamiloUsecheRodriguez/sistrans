package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Abono {

	@JsonProperty(value="cliente")
	private int cliente;
	
	@JsonProperty(value="funciones")
	private List<Integer> funciones;

	public Abono(@JsonProperty(value="cliente")int cliente,@JsonProperty(value="funciones") List<Integer> funciones) {
		super();
		this.cliente = cliente;
		this.funciones = funciones;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public List<Integer> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Integer> funciones) {
		this.funciones = funciones;
	}
	
	
	
}
