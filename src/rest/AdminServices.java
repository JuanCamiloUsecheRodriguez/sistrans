/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package rest;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import dtm.JMSManager;
import tm.FestivAndesMaster;
import tm.VideoAndesMaster;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/admin/...
 * @author Juan
 */
@Path("admin")
public class AdminServices {

	// Servicios REST tipo GET:
	


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
	
	/**
	 * Método que retorna el error formateo para responder el Json en caso de Exception
	 * @return String con el Json del error
	 */
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	/**
	 * Método que expone servicio REST usando POST para inicializar los servicios de JMS
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/admin/jmsInit
	 */
	@POST
	@Path("/jmsInit")
	public Response initApp() {
		try {
			JMSManager manager = JMSManager.darInstacia(new FestivAndesMaster(getPath()));
			initDataFromFile(manager);
			System.out.println("InitApp1");
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).build();
	}
	
	/**
	 * Método que inicializa los atributos basicos de JMSManager
	 * 
	 */
	public void initDataFromFile(JMSManager manager) {
		try {
			String contextPathP = context.getRealPath("WEB-INF/ConnectionData");
			String connectionDataPath = contextPathP + FestivAndesMaster.CONNECTION_DATA_FILE_NAME_REMOTE;
			
			File arch = new File(connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			String myQueue = prop.getProperty("myQueue");
			String topicAllUsuarios = prop.getProperty("topicAllUsuarios");
			int numberApps = Integer.parseInt(prop.getProperty("numberApps"));
			
			manager.setUpJMSManager(numberApps, myQueue, topicAllUsuarios);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
