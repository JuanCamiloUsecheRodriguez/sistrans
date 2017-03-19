package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaSitios {

	@JsonProperty(value="sitios")
	private List<Sitio> sitios;

	public ListaSitios(@JsonProperty(value="sitios")List<Sitio> sitios) {
		super();
		this.sitios = sitios;
	}

	public List<Sitio> getSitios() {
		return sitios;
	}

	public void setSitios(List<Sitio> sitios) {
		this.sitios = sitios;
	}
		
}
