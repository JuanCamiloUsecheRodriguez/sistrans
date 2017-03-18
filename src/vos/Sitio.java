package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Sitio {
	
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
	private Date horaInicio;
	
	@JsonProperty(value="horaFin")
	private Date horaFin;
	
	@JsonProperty(value="tipoSilleteria")
	private String tipoSilleteria;

	public Sitio(
			@JsonProperty(value="id")int id,
			@JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="tipo") String tipo,
			@JsonProperty(value="cupos") int cupos,
			@JsonProperty(value="accesibilidad") boolean accesibilidad,
			@JsonProperty(value="horaInicio")Date horaInicio,
			@JsonProperty(value="horaFin")Date horaFin,
			@JsonProperty(value="tipoSilleteria")String tipoSilleteria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.cupos = cupos;
		this.accesibilidad = accesibilidad;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.tipoSilleteria = tipoSilleteria;
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

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public String getTipoSilleteria() {
		return tipoSilleteria;
	}

	public void setTipoSilleteria(String tipoSilleteria) {
		this.tipoSilleteria = tipoSilleteria;
	}
	

}
