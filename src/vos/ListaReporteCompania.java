package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReporteCompania {
	@JsonProperty(value="reportes")
	private List<ReporteCompania> reportes;

	public ListaReporteCompania(@JsonProperty(value="reportes")List<ReporteCompania> reportes) {
		super();
		this.reportes = reportes;
	}

	public List<ReporteCompania> getReportes() {
		return reportes;
	}

	public void setReportes(List<ReporteCompania> reportes) {
		this.reportes = reportes;
	}
	

}
