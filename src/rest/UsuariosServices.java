package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DAOTablaUsuarios;
import tm.FestivAndesMaster;
import tm.VideoAndesMaster;
import vos.ListaBuenCliente;
import vos.ListaRFC11;
import vos.ListaUsuarios;
import vos.ListaVideos;
import vos.PeticionRFC11;

@Path("usuarios")
public class UsuariosServices {
	
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
			usuarios = tm.darUsuariosRemote();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	@PUT
	@Path("/reporte/compraBoletas")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON})
	public Response getConsultaRFC11(PeticionRFC11 peticion) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaRFC11 usuarios;
		
		try {
			usuarios = tm.consultarComprasRFC11(
					peticion.getIdGerente(),
					peticion.getFechaI(),
					peticion.getFechaF(), 
					peticion.getRequerimientos(), 
					peticion.getLocalidad());
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	@GET
	@Path("/{idGerente}/reporte/buenosClientes/{cantBoletas}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBuenosClientes(@PathParam("idGerente")int idGerente,@PathParam("cantBoletas")int cantboletas) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaBuenCliente clientes;
		
		try {
			clientes = tm.consultarBuenosClientes(idGerente, cantboletas);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}
	
	@GET
	@Path("/generar/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarDatos(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			tm.generarUsuarios(inicial,cant);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
}
