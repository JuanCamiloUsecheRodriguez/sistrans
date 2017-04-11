package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaNotas {

	@JsonProperty(value="notas")
	private List<NotaDebito> notas;

	public ListaNotas(@JsonProperty(value="funciones")List<NotaDebito> notas) {
		super();
		this.notas = notas;
	}

	public List<NotaDebito> getNotas() {
		return notas;
	}

	public void setNotas(List<NotaDebito> notas) {
		this.notas = notas;
	}	
	
}
