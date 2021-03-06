package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import vos.ListaNotas;
import vos.ListaReporteFuncion;
import vos.ListaUsuarios;
import vos.Preferencia;
import vos.ReporteFuncion;

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
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFunciones() {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaFunciones funciones;
		
		try {
			funciones = tm.darFuncionesRemote();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funciones).build();
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
	
	@GET
	@Path("/compania/{compania}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFuncionesCompania(@PathParam("compania")String compania,	@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaFunciones funciones;
		
		try {
			funciones = tm.darFuncionesCompania(compania,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funciones).build();
	}
	
	@GET
	@Path("/categoria/{categoria}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFuncionesCategoria(@PathParam("categoria")String categoria,	@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaFunciones funciones;
		
		try {
			funciones = tm.darFuncionesCategoria(categoria,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funciones).build();
	}
	
	@GET
	@Path("/idioma/{idioma}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFuncionesIdioma(@PathParam("idioma")String idioma,	@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaFunciones funciones;
		
		try {
			funciones = tm.darFuncionesIdioma(idioma,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funciones).build();
	}
	
	@GET
	@Path("/accesibilidad/{param}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFuncionesAccesibilidad(@PathParam("param")String accesibilidad,	@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaFunciones funciones;
		
		try {
			funciones = tm.darFuncionesAccesibilidad(accesibilidad,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funciones).build();
	}
	
	@GET
	@Path("/reporte/{idFuncion}/{order}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getReporteFuncion(@PathParam("idFuncion")int idFuncion,@PathParam("order")String order) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaReporteFuncion reporte;
		
		try {
			reporte = tm.darReporteFuncion(idFuncion,order);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporte).build();
	}
	
	@GET
	@Path("/reporte/RFC9/{companiaId}/{fechaI}/{fechaF}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsultarAsistenciaRFC9(@PathParam("companiaId")int companiaId,@PathParam("fechaI")String fechaI,@PathParam("fechaF")String fechaF) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaUsuarios reporte;
		
		try {
			reporte = tm.darAsistenciaRFC9(companiaId, fechaI, fechaF);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporte).build();
	}
	
	@GET
	@Path("/reporte/RFC10/{companiaId}/{fechaI}/{fechaF}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getConsultarAsistenciaRFC10(@PathParam("companiaId")int companiaId,@PathParam("fechaI")String fechaI,@PathParam("fechaF")String fechaF) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaUsuarios reporte;
		
		try {
			reporte = tm.darAsistenciaRFC10(companiaId, fechaI, fechaF);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reporte).build();
	}
	
	@DELETE
	@Path("/{idFuncion}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFuncion(@PathParam("idFuncion") int idFuncion) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaNotas notas = null;
		try {
			notas = tm.deleteFuncion(idFuncion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(notas).build();
	}
	
	@GET
	@Path("/generar/{inicial}/{final}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarDatos(@PathParam("inicial")int inicial,@PathParam("final")int cant) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		
		try {
			tm.generarFunciones(inicial,cant);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
}
