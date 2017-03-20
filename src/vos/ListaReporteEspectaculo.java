package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReporteEspectaculo {
	
	@JsonProperty(value="reportes")
	private List<ReporteEspectaculo> reportes;

	public ListaReporteEspectaculo(@JsonProperty(value="reportes")List<ReporteEspectaculo> reportes) {
		super();
		this.reportes = reportes;
	}

	public List<ReporteEspectaculo> getReportes() {
		return reportes;
	}

	public void setReportes(List<ReporteEspectaculo> reportes) {
		this.reportes = reportes;
	};
	
	

}
