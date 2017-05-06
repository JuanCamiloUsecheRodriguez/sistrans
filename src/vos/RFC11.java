package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class RFC11 {

	@JsonProperty(value="espectaculo")
	private String espectaculo;
	
	@JsonProperty(value="fecha")
	private Date fecha;
	
	@JsonProperty(value="sitio")
	private String sitio;
	
	@JsonProperty(value="cantidadBoletas")
	private int cantidadBoletas;

	public RFC11(@JsonProperty(value="espectaculo")String espectaculo,
			@JsonProperty(value="fecha")Date fecha, 
			@JsonProperty(value="sitio")String sitio, 
			@JsonProperty(value="cantidadBoletas")int cantidadBoletas) {
		super();
		this.espectaculo = espectaculo;
		this.fecha = fecha;
		this.sitio = sitio;
		this.cantidadBoletas = cantidadBoletas;
	}

	public String getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(String espectaculo) {
		this.espectaculo = espectaculo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getSitio() {
		return sitio;
	}

	public void setSitio(String sitio) {
		this.sitio = sitio;
	}

	public int getCantidadBoletas() {
		return cantidadBoletas;
	}

	public void setCantidadBoletas(int cantidadBoletas) {
		this.cantidadBoletas = cantidadBoletas;
	}
	
	
	
	
}
