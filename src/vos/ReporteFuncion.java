package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteFuncion {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="boletasVendidas")
	private int boletasVendidas;
	
	@JsonProperty(value="producido")
	private int producido;
	
	@JsonProperty(value="localidad")
	private int localidad;
	
	@JsonProperty(value="esCliente")
	private boolean esCliente;

	public ReporteFuncion(
			@JsonProperty(value="id")int id, 
			@JsonProperty(value="boletasVendidas")int boletasVendidas, 
			@JsonProperty(value="producido")int producido, 
			@JsonProperty(value="localidad")int localidad, 
			@JsonProperty(value="esCliente")boolean esCliente) {
		super();
		this.id = id;
		this.boletasVendidas = boletasVendidas;
		this.producido = producido;
		this.localidad = localidad;
		this.esCliente = esCliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getLocalidad() {
		return localidad;
	}

	public void setLocalidad(int localidad) {
		this.localidad = localidad;
	}

	public boolean isEsCliente() {
		return esCliente;
	}

	public void setEsCliente(boolean esCliente) {
		this.esCliente = esCliente;
	}
	
		

}
