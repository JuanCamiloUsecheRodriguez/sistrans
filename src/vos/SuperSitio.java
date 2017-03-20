package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class SuperSitio {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="cupos")
	private int cupos;
	
	@JsonProperty(value="accesibilidad")
	private boolean accesibilidad;
	
	@JsonProperty(value="horaInicio")
	private String horaInicio;
	
	@JsonProperty(value="horaFin")
	private String horaFin;
	
	@JsonProperty(value="tipoSilleteria")
	private String tipoSilleteria;
	
	@JsonProperty(value="idFuncion")
	private int idFuncion;
	
	@JsonProperty(value="nombreEspectaculo")
	private String nombreEspectaculo;
	
	@JsonProperty(value="localidad")
	private String localidad;
	
	@JsonProperty(value="precio")
	private int precio;

	public SuperSitio(
			@JsonProperty(value="id")int id, 
			@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="tipo")String tipo, 
			@JsonProperty(value="cupos")int cupos, 
			@JsonProperty(value="accesibilidad")boolean accesibilidad, 
			@JsonProperty(value="horaInicio")String horaInicio,
			@JsonProperty(value="horaFin")String horaFin, 
			@JsonProperty(value="tipoSilleteria")String tipoSilleteria, 
			@JsonProperty(value="idFuncion")int idFuncion, 
			@JsonProperty(value="nombreEspectaculo")String nombreEspectaculo, 
			@JsonProperty(value="localidad")String localidad,
			@JsonProperty(value="precio")int precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.cupos = cupos;
		this.accesibilidad = accesibilidad;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.tipoSilleteria = tipoSilleteria;
		this.idFuncion = idFuncion;
		this.nombreEspectaculo = nombreEspectaculo;
		this.localidad = localidad;
		this.precio = precio;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCupos() {
		return cupos;
	}

	public void setCupos(int cupos) {
		this.cupos = cupos;
	}

	public boolean isAccesibilidad() {
		return accesibilidad;
	}

	public void setAccesibilidad(boolean accesibilidad) {
		this.accesibilidad = accesibilidad;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getTipoSilleteria() {
		return tipoSilleteria;
	}

	public void setTipoSilleteria(String tipoSilleteria) {
		this.tipoSilleteria = tipoSilleteria;
	}

	public int getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}

	public String getNombreEspectaculo() {
		return nombreEspectaculo;
	}

	public void setNombreEspectaculo(String nombreEspectaculo) {
		this.nombreEspectaculo = nombreEspectaculo;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	
}
