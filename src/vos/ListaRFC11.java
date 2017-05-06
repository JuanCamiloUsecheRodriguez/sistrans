package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRFC11 {

	@JsonProperty(value="reportes")
	private List<RFC11> reportes;

	public ListaRFC11(@JsonProperty(value="reportes")List<RFC11> reportes) {
		super();
		this.reportes = reportes;
	}

	public List<RFC11> getReportes() {
		return reportes;
	}

	public void setReportes(List<RFC11> reportes) {
		this.reportes = reportes;
	}
	
	
}
