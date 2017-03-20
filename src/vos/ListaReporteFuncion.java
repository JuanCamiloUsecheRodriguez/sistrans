package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReporteFuncion {
	
	@JsonProperty(value="reportes")
	private List<ReporteFuncion> reportes;

	public ListaReporteFuncion(@JsonProperty(value="reportes")List<ReporteFuncion> reportes) {
		super();
		this.reportes = reportes;
	}

	public List<ReporteFuncion> getReportes() {
		return reportes;
	}

	public void setReportes(List<ReporteFuncion> reportes) {
		this.reportes = reportes;
	}
	
	

}
