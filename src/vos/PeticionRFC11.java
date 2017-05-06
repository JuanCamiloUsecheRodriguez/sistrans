package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PeticionRFC11 {
	
	@JsonProperty(value="idGerente")
	private int idGerente;
	
	@JsonProperty(value="fechaI")
	private String fechaI;
	
	@JsonProperty(value="fechaF")
	private String fechaF;
	
	@JsonProperty(value="requerimientos")
	private String[] requerimientos;
	
	@JsonProperty(value="localidad")
	private String localidad;

	public PeticionRFC11(
			@JsonProperty(value="idGerente")int idGerente, 
			@JsonProperty(value="fechaI")String fechaI, 
			@JsonProperty(value="requerimientos")String fechaF, 
			@JsonProperty(value="requerimientos")String[] requerimientos, 
			@JsonProperty(value="localidad")String localidad) {
		super();
		this.idGerente = idGerente;
		this.fechaI = fechaI;
		this.fechaF = fechaF;
		this.requerimientos = requerimientos;
		this.localidad = localidad;
	}

	public int getIdGerente() {
		return idGerente;
	}

	public void setIdGerente(int idGerente) {
		this.idGerente = idGerente;
	}

	public String getFechaI() {
		return fechaI;
	}

	public void setFechaI(String fechaI) {
		this.fechaI = fechaI;
	}

	public String getFechaF() {
		return fechaF;
	}

	public void setFechaF(String fechaF) {
		this.fechaF = fechaF;
	}

	public String[] getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(String[] requerimientos) {
		this.requerimientos = requerimientos;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	
	

}
