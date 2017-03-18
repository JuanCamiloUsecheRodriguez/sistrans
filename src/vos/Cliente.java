package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente {

	/**
	 * Edad
	 */
	@JsonProperty(value="edad")
	private int edad;
	
	/**
	 * Preferencias
	 */
	@JsonProperty(value="preferencias")
	private List<Categoria> preferencias;

	public Cliente(@JsonProperty(value="edad")int edad,@JsonProperty(value="preferencias") List<Categoria> preferencias) {
		super();
		this.edad = edad;
		this.preferencias = preferencias;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public List<Categoria> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<Categoria> preferencias) {
		this.preferencias = preferencias;
	}
	
}
