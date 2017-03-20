package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.ListaFunciones;
import vos.ListaUsuarios;
import vos.Preferencia;

@Path("funciones")
public class FuncionesServices {
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
	
	@PUT
	@Path("/{idFuncion}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRealizacionDeFuncion(@PathParam("idFuncion")int idFuncion) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.registrarRealizacionFuncion(idFuncion);;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity("{\"Realizada\": \"Y\"}").build();
	}
	
	@GET
	@Path("/{fecha1}/{fecha2}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFuncionesRangoFecha(@PathParam("fecha1")String fechaInicial,
			@PathParam("fecha2")String fechaFin,
			@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaFunciones funciones;
		
		try {
			funciones = tm.darFuncionesRangoFecha(fechaInicial,fechaFin,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funciones).build();
	}
}
