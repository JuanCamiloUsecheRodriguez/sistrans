package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import tm.VideoAndesMaster;
import vos.Boleta;
import vos.BoletaDetail;
import vos.CompraBoletas;
import vos.ListaBoletas;
import vos.ListaVideos;
import vos.Preferencia;

@Path("boletas")
public class BoletasServices {
	
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
	
	@POST
	//@Path("/boletas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBoleta(CompraBoletas listaBoletas) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.addBoleta(listaBoletas);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(listaBoletas).build();
	}
	
	@GET
	@Path("/{idBoleta}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBoletasPorId(@PathParam("idBoleta") int idBoleta) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaBoletas boletas;
		try {
			boletas = tm.darBoletasPorId(idBoleta);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(boletas).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBoletas() {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaBoletas boletas;
		try {
			boletas = tm.darBoletas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(boletas).build();
	}

}
