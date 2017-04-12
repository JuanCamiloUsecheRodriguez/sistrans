package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import tm.VideoAndesMaster;
import vos.Abono;
import vos.ListaReporteAsistencia;
import vos.NotaDebito;
import vos.Preferencia;
import vos.Video;

@Path("clientes")
public class ClientesServices {

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
	@Path("/{idUsuario}/reporteAsistencia")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarAsistencia(@PathParam("idUsuario")int idUsuario){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaReporteAsistencia reporte = null;
		try {
			reporte = tm.darReporteAsistencia(idUsuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporte).build();
	}
	
	
	
	@POST
	@Path("/abono")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAbono(Abono abono) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.addAbono(abono);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(abono).build();
	}
	
	@POST
	@Path("/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferencia(Preferencia pref) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.addPreferencia(pref);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pref).build();
	}
	
	@PUT
	@Path("/preferencias/{anterior}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePreferencia(Preferencia pref,@PathParam("anterior")int anterior ) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.actualizarPreferencia(anterior, pref);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pref).build();
	}
	
	@DELETE
	@Path("/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePref(Preferencia pref) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.deletePreferencia(pref);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pref).build();
	}
	
	@DELETE
	@Path("abono/{idCliente}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBoleta(@PathParam("idCliente") int idCliente) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		NotaDebito nota = null;
		try {
			nota = tm.deleteAbono(idCliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(nota).build();
	}
}
