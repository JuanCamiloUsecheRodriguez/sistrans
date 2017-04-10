package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Boleta {

	/**
	 * Id del video
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Numero de la silla
	 */
	@JsonProperty(value="silla")
	private int silla;
	
	/**
	 * Localidad
	 */
	@JsonProperty(value="localidad")
	private String localidad;
	
	/**
	 * Usuario
	 */
	@JsonProperty(value="usuario")
	private int usuario;
	
	/**
	 * Funci�n
	 */
	@JsonProperty(value="funcion")
	private int funcion;
	
	/**
	 * Cosntructor
	 * @param id
	 * @param silla
	 * @param precio
	 * @param cupos
	 * @param localidad
	 */
	public Boleta(@JsonProperty(value="id")int id,	@JsonProperty(value="silla")int silla, @JsonProperty(value="localidad")String localidad,  @JsonProperty(value="usuario") int usuario, @JsonProperty(value="funcion") int funcion) {
		super();
		this.id = id;
		this.silla = silla;
		this.localidad = localidad;
		this.usuario = usuario;
		this.funcion = funcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSilla() {
		return silla;
	}

	public void setSilla(int silla) {
		this.silla = silla;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public int getUsario() {
		return usuario;
	}

	public void setUsario(int usario) {
		this.usuario = usario;
	}

	public int getFuncion() {
		return funcion;
	}

	public void setFuncion(int funcion) {
		this.funcion = funcion;
	}

	
	
	
}
