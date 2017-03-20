package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteEspectaculo {
	
	@JsonProperty(value="idEspectaculo")
	private int idEspectaculo;
	
	@JsonProperty(value="idFuncion")
	private int idFuncion;
	
	@JsonProperty(value="boletasVendidas")
	private int boletasVendidas;
	
	@JsonProperty(value="producido")
	private int producido;
	
	@JsonProperty(value="cupos")
	private int cupos;
	
	@JsonProperty(value="ocupados")
	private int ocupados;
	
	@JsonProperty(value="porentaje")
	private String porentaje;
	
	@JsonProperty(value="esCliente")
	private boolean esCliente;
	
	@JsonProperty(value="sitio")
	private int sitio;

	public ReporteEspectaculo(
			@JsonProperty(value="idEspectaculo")int idEspectaculo, 
			@JsonProperty(value="idFuncion")int idFuncion, 
			@JsonProperty(value="boletasVendidas")int boletasVendidas, 
			@JsonProperty(value="producido")int producido, 
			@JsonProperty(value="cupos")int cupos,
			@JsonProperty(value="ocupados")int ocupados, 
			@JsonProperty(value="porentaje")String porentaje, 
			@JsonProperty(value="esCliente")boolean esCliente, 
			@JsonProperty(value="sitio")int sitio) {
		super();
		this.idEspectaculo = idEspectaculo;
		this.idFuncion = idFuncion;
		this.boletasVendidas = boletasVendidas;
		this.producido = producido;
		this.cupos = cupos;
		this.ocupados = ocupados;
		this.porentaje = porentaje;
		this.esCliente = esCliente;
		this.sitio = sitio;
	}

	public int getIdEspectaculo() {
		return idEspectaculo;
	}

	public void setIdEspectaculo(int idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}

	public int getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}

	public int getBoletasVendidas() {
		return boletasVendidas;
	}

	public void setBoletasVendidas(int boletasVendidas) {
		this.boletasVendidas = boletasVendidas;
	}

	public int getProducido() {
		return producido;
	}

	public void setProducido(int producido) {
		this.producido = producido;
	}

	public int getCupos() {
		return cupos;
	}

	public void setCupos(int cupos) {
		this.cupos = cupos;
	}

	public int getOcupados() {
		return ocupados;
	}

	public void setOcupados(int ocupados) {
		this.ocupados = ocupados;
	}

	public String getPorentaje() {
		return porentaje;
	}

	public void setPorentaje(String porentaje) {
		this.porentaje = porentaje;
	}

	public boolean isEsCliente() {
		return esCliente;
	}

	public void setEsCliente(boolean esCliente) {
		this.esCliente = esCliente;
	}

	public int getSitio() {
		return sitio;
	}

	public void setSitio(int sitio) {
		this.sitio = sitio;
	}
	

}
