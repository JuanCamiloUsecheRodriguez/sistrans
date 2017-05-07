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
import vos.ListaFunciones;
import vos.ListaSitios;
import vos.SuperSitio;

@Path("sitios")
public class SitiosServices {

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
	@Path("/{index}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSitioCompleto(@PathParam("index")int idSitio,@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		SuperSitio supersitio;
		
		try {
			supersitio = tm.darSitiosCompleto(idSitio,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(supersitio).build();
	}
	
	@GET
	@Path("/generar/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarDatos(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			tm.generarSitios(inicial,cant);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("/generarLocalidades/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarLocalidades(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			tm.generarLocalidades(inicial, cant);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("/generarCompania/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarLocalidad(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			tm.generarLocalidades(inicial,cant);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("/generarReq/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarReq(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			tm.generarRequerimientos(inicial, cant);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}

}
