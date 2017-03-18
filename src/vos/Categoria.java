package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Categoria {

	/**
	 * Id
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * nombre
	 */
	@JsonProperty(value="nombre")
	private int nombre;

	public Categoria(@JsonProperty(value="id")int id, @JsonProperty(value="nombre") int nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}
}
