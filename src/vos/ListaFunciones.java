package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFunciones {

	@JsonProperty(value="funciones")
	private List<Funcion> funciones;

	public ListaFunciones(@JsonProperty(value="funciones")List<Funcion> funciones) {
		super();
		this.funciones = funciones;
	}

	public List<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}	
	
}
