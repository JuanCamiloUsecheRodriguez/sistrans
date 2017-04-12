package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteAsistencia {
	
	@JsonProperty(value="idCliente")
	private int idCliente;
	
	@JsonProperty(value="idFuncion")
	private int idFuncion;
	
	@JsonProperty(value="idBoleta")
	private int idBoleta;
	
	@JsonProperty(value="funcionRealizada")
	private boolean funcionRealizada;
	
	@JsonProperty(value="devuelta")
	private boolean devuelta;

	public ReporteAsistencia(
			@JsonProperty(value="idCliente")int idCliente, 
			@JsonProperty(value="idFuncion")int idFuncion, 
			@JsonProperty(value="idBoleta")int idBoleta, 
			@JsonProperty(value="funcionRealizada")boolean funcionRealizada, 
			@JsonProperty(value="devuelta")boolean devuelta) {
		super();
		this.idCliente = idCliente;
		this.idFuncion = idFuncion;
		this.idBoleta = idBoleta;
		this.funcionRealizada = funcionRealizada;
		this.devuelta = devuelta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}

	public int getIdBoleta() {
		return idBoleta;
	}

	public void setIdBoleta(int idBoleta) {
		this.idBoleta = idBoleta;
	}

	public boolean isFuncionRealizada() {
		return funcionRealizada;
	}

	public void setFuncionRealizada(boolean funcionRealizada) {
		this.funcionRealizada = funcionRealizada;
	}

	public boolean isDevuelta() {
		return devuelta;
	}

	public void setDevuelta(boolean devuelta) {
		this.devuelta = devuelta;
	}
	
	
	

}
