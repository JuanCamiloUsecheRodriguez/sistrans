package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import vos.Espectaculo;
import vos.ReporteEspectaculo;
import vos.ReporteFuncion;

public class DAOTablaEspectaculos {

	/**
	 * Arraylits de recursos que se usan para la ejecuci贸n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi贸n a la base de datos
	 */
	private Connection conn;

	/**
	 * M茅todo constructor que crea DAOEspectaculo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaEspectaculos() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * M茅todo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * M茅todo que inicializa la connection del DAO a la base de datos con la conexi贸n que entra como par谩metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * M茅todo que, usando la conexi贸n a la base de datos, saca todos los Espectaculos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM Espectaculos;
	 * @return Arraylist con los Espectaculos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Espectaculo> darEspectaculos() throws SQLException, Exception {
		ArrayList<Espectaculo> Espectaculos = new ArrayList<Espectaculo>();

		String sql = "SELECT * FROM ESPECTACULO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
	
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			String formato = rs.getString("FORMATO");
			int duracion = Integer.parseInt(rs.getString("DURACION"));
			String idioma = rs.getString("IDIOMA");
			Date fechaInicio = rs.getDate("FECHAINICIO");
			Date fechaFin= rs.getDate("FECHAFIN");
			String clasificacion = rs.getString("CLASIFICACION");
			int costo = Integer.parseInt(rs.getString("COSTO"));
	
			String bInteractivo = rs.getString("INTERACTIVO");
			boolean interactivo = false;
			if(bInteractivo.equals("T"))
			{
				interactivo = true;
			}
			else interactivo = false;
			
			String descripcion = rs.getString("DESCRIPCION");
			
			Espectaculos.add(new Espectaculo(id, nombre, formato, duracion, idioma, fechaInicio, fechaFin, clasificacion, costo, interactivo, descripcion));
		}
		return Espectaculos;
	}
	public Espectaculo darEspectaculoPorId(int pId) throws NumberFormatException, SQLException {
		Espectaculo espectaculo = null;

		String sql = "SELECT * FROM ESPECTACULO WHERE ID ='" + pId + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {

			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			String formato = rs.getString("FORMATO");
			int duracion = Integer.parseInt(rs.getString("DURACION"));
			String idioma = rs.getString("IDIOMA");
			Date fechaInicio = rs.getDate("FECHAINICIO");
			Date fechaFin= rs.getDate("FECHAFIN");
			String clasificacion = rs.getString("CLASIFICACION");
			int costo = Integer.parseInt(rs.getString("COSTO"));
	
			String bInteractivo = rs.getString("INTERACTIVO");
			boolean interactivo = false;
			if(bInteractivo.equals("Y"))
			{
				interactivo = true;
			}
			else interactivo = false;
			
			String descripcion = rs.getString("DESCRIPCION");
			
			espectaculo = new Espectaculo(id, nombre, formato, duracion, idioma, fechaInicio, fechaFin, clasificacion, costo, interactivo, descripcion);
		}

		return espectaculo;
	}
	
	public ArrayList<ReporteEspectaculo> darReporteEspectaculo(int pIdEspectaculo, String ordenamiento) throws SQLException
	{
		 ArrayList<ReporteEspectaculo> reporte = new ArrayList<ReporteEspectaculo>();
		 String sql = 	"SELECT IDESPECTACULO,FUNCION,COUNT(IDBOLETA),SUM(PRECIO),CUPOS,OCUPADOS,(OCUPADOS/CUPOS)*100,ID_CLIENTE,IDSITIO FROM "+
				 		"(SELECT* FROM "+
						"(SELECT * FROM "+
						"(SELECT IDESPECTACULO,IDFUNCION,SITIO FROM "+
						 "(SELECT ID AS IDESPECTACULO FROM ESPECTACULO)E1 "+
						 "INNER JOIN "+
						 "(SELECT ID AS IDFUNCION,SITIO,ESPECTACULO FROM FUNCION)E2 ON E1.IDESPECTACULO = E2.ESPECTACULO)T1 "+
						 "INNER JOIN "+
						 "(SELECT ID AS IDBOLETA,PRECIO,CUPOS AS OCUPADOS,FUNCION,USUARIODOC FROM BOLETA)T2 ON T1.IDFUNCION = T2.FUNCION)M1 "+
						 "INNER JOIN "+
						 "(SELECT ID AS IDSITIO,CUPOS FROM SITIO )M2 ON M1.SITIO = M2.IDSITIO)G1 "+
						 "FULL OUTER JOIN "+
						 "(SELECT ID_CLIENTE FROM CLIENTE)G2 ON G1.USUARIODOC = G2.ID_CLIENTE "+
						 " WHERE IDESPECTACULO = "+pIdEspectaculo+ " GROUP BY IDESPECTACULO,FUNCION,ID_CLIENTE,IDSITIO,CUPOS,OCUPADOS ORDER BY IDESPECTACULO " + ordenamiento;
		
		 System.out.println(sql);
		 PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idEspectaculo = Integer.parseInt(rs.getString(1));
			int idFuncion = Integer.parseInt(rs.getString(2));
			int boletasVendidas = Integer.parseInt(rs.getString(3));
			int producido = Integer.parseInt(rs.getString(4));
			int cupos = Integer.parseInt(rs.getString(5));
			int ocupados = Integer.parseInt(rs.getString(6));
			String porcentaje = rs.getString(7);
			String idCliente = rs.getString(8);
			boolean esCliente = idCliente==null?true:false;
			int sitio = Integer.parseInt(rs.getString(9));
			
			reporte.add(new ReporteEspectaculo(idEspectaculo, idFuncion, boletasVendidas, producido, cupos, ocupados, porcentaje, esCliente, sitio));
		}
		return reporte;
	}
	
	public void generarCompanias(int inicial, int cant) throws SQLException{
		for (int i = inicial; i < cant; i++) {
			int id = i;
			String nombre = "Nombre" + i;
			String web = "www.web" + i +".com";
			int random = (int) (Math.random() * 3) + 1;
			String fechaLlegada = null;
			String fechaSalida = null;
			if(random == 1){
				fechaLlegada = "24/05/2017";
				fechaSalida = "12/06/2017";
			}
			if(random ==2){
				fechaLlegada = "27/05/2017";
				fechaSalida = "17/06/2017";
			}
			else
			{
				fechaLlegada = "29/05/2017";
				fechaSalida = "19/06/2017";
			}
			
			String sql = "INSERT INTO COMPANIA VALUES"
					+ "("+id+",'"+nombre+"','"+web+"',TO_DATE('"+ fechaLlegada +"','DD/MM/RR'),TO_DATE('"+ fechaSalida +"','DD/MM/RR'))";
				
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				System.out.println("SQL stmt:" + sql);
				ResultSet rs = prepStmt.executeQuery();
		}
	}
	
	public void generarPatrocinas(int inicialCompanias, int inicialEspectaculos, int cant) throws SQLException{
		for (int i = inicialCompanias; i < cant; i++, inicialEspectaculos++) {
			int idCompania = i;
			int idEspectaculo = inicialEspectaculos;
			String sql = "INSERT INTO PATROCINA VALUES"
					+ "("+idCompania+","+idEspectaculo+")";
				PreparedStatement prepStmt = conn.prepareStatement(sql);
				recursos.add(prepStmt);
				System.out.println("SQL stmt:" + sql);
				ResultSet rs = prepStmt.executeQuery();
		}
	}
	
	public void generarEspectaculo(int inicial, int cant) throws SQLException{
		for (int i = inicial; i < cant; i++) {
			int id = i;
			String nombre = "nombre" + i;
			String formato = null;
			int duracion = 60;
			String idioma = null;
			String fechaLlegada = null;
			String fechaSalida = null;
			String clasificacion = null;
			int costo = 40000;
			String interactivo = null;
			String descripcion = "descripcion" + i;
			int random = (int) (Math.random() * 3) + 1;
			if(random == 1){
				formato = "Obra";
				idioma = "Espa駉l";
				fechaLlegada = "24/05/2017";
				fechaSalida = "12/06/2017";
				clasificacion = "TODOPUBLICO";
				interactivo = "N";
			}
			if(random ==2){
				formato = "Musical";
				idioma = "Ingles";
				fechaLlegada = "27/05/2017";
				fechaSalida = "17/06/2017";
				clasificacion = "ADULTOS";
				interactivo = "N";
			}
			else
			{
				formato = "Titeres";
				idioma = "Frances";
				fechaLlegada = "29/05/2017";
				fechaSalida = "19/06/2017";
				clasificacion = "NINOS";
				interactivo = "Y";
			}
			String sql = "INSERT INTO ESPECTACULO VALUES"
					+ "("+id+",'"+nombre+"','"+formato+"',"+duracion+",'"+idioma+"','"+fechaLlegada+"','"+fechaSalida+"','"+clasificacion+"',"+costo+",'"+interactivo+"','"+descripcion+"')";
					
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			System.out.println("SQL stmt:" + sql);
			ResultSet rs = prepStmt.executeQuery();
		}
	}
}
