package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Espectaculo {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="formato")
	private String formato;
	
	@JsonProperty(value="duracion")
	private int duracion;
	
	@JsonProperty(value="fechaInicio")
	private Date fechaInicio;
	
	@JsonProperty(value="fechaFin")
	private Date fechaFin;
	
	@JsonProperty(value="clasificacion")
	private String clasificacion;
	
	@JsonProperty(value="costo")
	private int costo;
	
	@JsonProperty(value="interactivo")
	private boolean interactivo;
	
	@JsonProperty(value="descripcion")
	private String descripcion;

	public Espectaculo(
			@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="formato")String formato, 
			@JsonProperty(value="duracion")int duracion, 
			@JsonProperty(value="fechaInicio")Date fechaInicio, 
			@JsonProperty(value="fechaFin")Date fechaFin,
			@JsonProperty(value="clasificacion")String clasificacion, 
			@JsonProperty(value="costo")int costo, 
			@JsonProperty(value="interactivo")boolean interactivo, 
			@JsonProperty(value="descripcion")String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.formato = formato;
		this.duracion = duracion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.clasificacion = clasificacion;
		this.costo = costo;
		this.interactivo = interactivo;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public boolean isInteractivo() {
		return interactivo;
	}

	public void setInteractivo(boolean interactivo) {
		this.interactivo = interactivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	

}
