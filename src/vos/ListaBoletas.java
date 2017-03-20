package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaBoletas {

	@JsonProperty(value="boletas")
	private List<BoletaDetail> boletas;
	
	public ListaBoletas(@JsonProperty(value="boletas")List<BoletaDetail> boletas){
		this.boletas = boletas;
	}

	public List<BoletaDetail> getBoletas() {
		return boletas;
	}

	public void setBoletas(List<BoletaDetail> boletas) {
		this.boletas = boletas;
	}
	
}
