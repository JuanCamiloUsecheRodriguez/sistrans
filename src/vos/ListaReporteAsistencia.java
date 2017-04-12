package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReporteAsistencia {
	
	@JsonProperty(value="reportes")
	private List<ReporteAsistencia> reportes;

	public ListaReporteAsistencia(@JsonProperty(value="reportes")List<ReporteAsistencia> reportes) {
		super();
		this.reportes = reportes;
	}

	public List<ReporteAsistencia> getReportes() {
		return reportes;
	}

	public void setReportes(List<ReporteAsistencia> reportes) {
		this.reportes = reportes;
	}
	

}
