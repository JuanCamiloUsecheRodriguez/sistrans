package vos;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReporteCompania {
	@JsonProperty(value="reportes")
	private List<ReporteCompania> reportes;

	public ListaReporteCompania(@JsonProperty(value="reportes")List<ReporteCompania> reportes) {
		super();
		this.reportes = reportes;
	}
	
	public ListaReporteCompania() {
		reportes = new ArrayList<ReporteCompania>();
	}

	public List<ReporteCompania> getReportes() {
		return reportes;
	}

	public void setReportes(List<ReporteCompania> reportes) {
		this.reportes = reportes;
	}
	
	public void addReporteCompania(ListaReporteCompania reporteCompaniasNew){
		this.reportes.addAll(reporteCompaniasNew.getReportes());
	}

}
