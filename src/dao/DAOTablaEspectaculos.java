package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import vos.Espectaculo;

public class DAOTablaEspectaculos {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOEspectaculo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaEspectaculos() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
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
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Método que, usando la conexión a la base de datos, saca todos los Espectaculos de la base de datos
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
			if(bInteractivo.equals("T"))
			{
				interactivo = true;
			}
			else interactivo = false;
			
			String descripcion = rs.getString("DESCRIPCION");
			
			espectaculo = new Espectaculo(id, nombre, formato, duracion, idioma, fechaInicio, fechaFin, clasificacion, costo, interactivo, descripcion);
		}

		return espectaculo;
	}

}
