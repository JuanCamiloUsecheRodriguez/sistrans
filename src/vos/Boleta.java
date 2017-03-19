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
	private String silla;
	
	/**
	 * Precio de la boleta
	 */
	@JsonProperty(value="precio")
	private Integer precio;
	
	/**
	 * Cupos
	 */
	@JsonProperty(value="cupos")
	private Integer cupos;
	
	/**
	 * Localidad
	 */
	@JsonProperty(value="localidad")
	private String localidad;
	
	/**
	 * Usuario
	 */
	@JsonProperty(value="usuario")
	private Usuario usario;
	
	/**
	 * Función
	 */
	@JsonProperty(value="funcion")
	private Funcion funcion;
	
	/**
	 * Cosntructor
	 * @param id
	 * @param silla
	 * @param precio
	 * @param cupos
	 * @param localidad
	 */
	public Boleta(@JsonProperty(value="id")int id,	@JsonProperty(value="silla")String silla, @JsonProperty(value="precio")Integer precio,@JsonProperty(value="cupos") Integer cupos, @JsonProperty(value="localidad")String localidad,  @JsonProperty(value="usuario") Usuario usuario, @JsonProperty(value="funcion") Funcion funcion) {
		super();
		this.id = id;
		this.silla = silla;
		this.precio = precio;
		this.cupos = cupos;
		this.localidad = localidad;
		this.usario = usuario;
		this.funcion = funcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSilla() {
		return silla;
	}

	public void setSilla(String silla) {
		this.silla = silla;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Integer getCupos() {
		return cupos;
	}

	public void setCupos(Integer cupos) {
		this.cupos = cupos;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Usuario getUsario() {
		return usario;
	}

	public void setUsario(Usuario usario) {
		this.usario = usario;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	
	
	
}
