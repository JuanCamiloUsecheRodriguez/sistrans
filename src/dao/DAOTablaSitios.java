package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Sitio;
import vos.Video;

public class DAOTablaSitios {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaSitios() {
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

	public ArrayList<Sitio> darSitios() throws SQLException, Exception {
		ArrayList<Sitio> sitios = new ArrayList<Sitio>();

		String sql = "SELECT * FROM SITIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			int cupos = Integer.parseInt(rs.getString("CUPOS"));
			boolean accesibilidad = rs.getString("ACCESIBILIDAD") =="Y"?true:false;
			String horaInicio = rs.getString(6);
			String horaFin = rs.getString(7);
			String tipoSillas = rs.getString("TIPOSILLETERIA");
			sitios.add(new Sitio(id, nombre, tipo, cupos, accesibilidad, horaInicio, horaFin, tipoSillas));
		}
		return sitios;
	}

	public Sitio darSitioPorId(int pId) throws SQLException, Exception{
		Sitio sitio = null;

		String sql = "SELECT * FROM SITIO WHERE ID = '"+pId+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			int cupos = Integer.parseInt(rs.getString("CUPOS"));
			boolean accesibilidad = rs.getString("ACCESIBILIDAD") =="Y"?true:false;
			String horaInicio = rs.getString(6);
			String horaFin = rs.getString(7);
			String tipoSillas = rs.getString("TIPOSILLETERIA");
			sitio =new Sitio(id, nombre, tipo, cupos, accesibilidad, horaInicio, horaFin, tipoSillas);
		}
		return sitio;
	}
	
}
