package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.ListaReporteEspectaculo;
import vos.ListaReporteFuncion;

@Path("espectaculos")
public class EspectaculosServices {
	
	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Path("/reporte/{idEspectaculo}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getReporteEspectaculo(@PathParam("idEspectaculo")int idEspectaculo,@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaReporteEspectaculo reporte;
		
		try {
			reporte = tm.darReporteEspectaculo(idEspectaculo,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporte).build();
	}

	@GET
	@Path("/generarCompania/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarDatos(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			tm.generarCompania(inicial,cant);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}

	@GET
	@Path("/generarPatrocina/{inicialcom}/{inicialespec}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarDatos(@PathParam("inicialcom")int inicialcom, @PathParam("inicialespec")int inicialespec, @PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			//tm.generarPatrocina(inicialcom,inicialespec,cant);
			tm.generarPatrocina(100,200, 800);
			tm.generarPatrocina(200,400, 800);
			tm.generarPatrocina(300,500, 800);
			tm.generarPatrocina(400,600, 800);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("/generar/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarDatosEspectaculo(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			
			tm.generarEspectaculo(1000, 1250);
			tm.generarEspectaculo(1250, 1500);
			tm.generarEspectaculo(1500, 1750);
			tm.generarEspectaculo(1750, 2000);
			tm.generarEspectaculo(2000, 2250);
			tm.generarEspectaculo(2250, 2500);
			tm.generarEspectaculo(2500, 2750);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
}
