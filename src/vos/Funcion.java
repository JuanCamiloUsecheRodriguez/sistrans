package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcion {
	
	@JsonProperty(value="idfuncion")
	private int idfuncion;
	
	@JsonProperty(value="fecha")
	private Date fecha;
	
	@JsonProperty(value="idsitio")
	private int idsitio;
	
	@JsonProperty(value="idespectaculo")
	private int idespectaculo;
	
	@JsonProperty(value="realizado")
	private boolean realizado;

	public Funcion(
			@JsonProperty(value="idfuncion")int idfuncion,
			@JsonProperty(value="fecha")Date fecha, 
			@JsonProperty(value="idsitio")int idsitio, 
			@JsonProperty(value="idespectaculo")int idespectaculo, 
			@JsonProperty(value="realizado")boolean realizado) {
		super();
		this.idfuncion = idfuncion;
		this.fecha = fecha;
		this.idsitio = idsitio;
		this.idespectaculo = idespectaculo;
		this.realizado = realizado;
	}

	public int getIdfuncion() {
		return idfuncion;
	}

	public void setIdfuncion(int idfuncion) {
		this.idfuncion = idfuncion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getIdsitio() {
		return idsitio;
	}

	public void setIdsitio(int idsitio) {
		this.idsitio = idsitio;
	}

	public int getIdespectaculo() {
		return idespectaculo;
	}

	public void setIdespectaculo(int idespectaculo) {
		this.idespectaculo = idespectaculo;
	}

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}

	

}
