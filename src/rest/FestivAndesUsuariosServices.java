package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DAOTablaUsuarios;
import tm.FestivAndesMaster;
import tm.VideoAndesMaster;
import vos.ListaUsuarios;
import vos.ListaVideos;

@Path("usuarios")
public class FestivAndesUsuariosServices {
	
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUsuarios() {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaUsuarios usuarios;
		
		try {
			usuarios = tm.darUsuarios();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
}
