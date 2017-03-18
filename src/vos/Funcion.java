package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcion {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="fecha")
	private Date fecha;
	
	@JsonProperty(value="sitio")
	private Sitio sitio;
	
	@JsonProperty(value="espectaculo")
	private Espectaculo espectaculo;
	
	@JsonProperty(value="realizada")
	private boolean realizada;

	public Funcion(
			@JsonProperty(value="id")int id,
			@JsonProperty(value="fecha")Date fecha, 
			@JsonProperty(value="sitio")Sitio sitio, 
			@JsonProperty(value="espectaculo")Espectaculo espectaculo, 
			@JsonProperty(value="realizada")boolean realizada) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.sitio = sitio;
		this.espectaculo = espectaculo;
		this.realizada = realizada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Sitio getSitio() {
		return sitio;
	}

	public void setSitio(Sitio sitio) {
		this.sitio = sitio;
	}

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}
	
	
	

}
