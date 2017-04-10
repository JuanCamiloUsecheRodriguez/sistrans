package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CompraBoletas {

	@JsonProperty(value="boletas")
	private List<Boleta> boletas;
	
	public CompraBoletas(@JsonProperty(value="boletas")List<Boleta> boletas){
		this.boletas = boletas;
	}

	public List<Boleta> getBoletas() {
		return boletas;
	}

	public void setBoletas(List<Boleta> boletas) {
		this.boletas = boletas;
	}
	
}
