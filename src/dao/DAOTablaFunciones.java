package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Espectaculo;
import vos.Funcion;
import vos.Sitio;
import vos.Video;

public class DAOTablaFunciones {

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
	public DAOTablaFunciones() {
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
	
	
	public ArrayList<Funcion> darFunciones() throws SQLException, Exception {
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();

		String sql = "SELECT * FROM FUNCION";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString(1));
			Date fecha = rs.getDate(2);
			DAOTablaSitios daoSitios = new DAOTablaSitios();
			daoSitios.setConn(conn);
			Sitio sitio = daoSitios.darSitioPorId(Integer.parseInt(rs.getString(3)));
			daoSitios.cerrarRecursos();
			DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
			Espectaculo espectaculo = daoEspectaculos.darEspectaculoPorId(Integer.parseInt(rs.getString(4)));
			daoEspectaculos.cerrarRecursos();
			daoEspectaculos.setConn(conn);
			
			boolean realizada = rs.getString("REALIZADA") =="Y"?true:false;
			funciones.add(new Funcion(id, fecha, sitio, espectaculo, realizada));
		}
		return funciones;
	}
	
	public Funcion darFuncionPorId(int pId) throws SQLException, Exception {
		Funcion funcion = null;
		
		String sql = "SELECT * FROM FUNCION WHERE ID ='"+pId+"'";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			int id = Integer.parseInt(rs.getString(1));
			Date fecha = rs.getDate(2);
			DAOTablaSitios daoSitios = new DAOTablaSitios();
			daoSitios.setConn(conn);
			Sitio sitio = daoSitios.darSitioPorId(Integer.parseInt(rs.getString(3)));
			daoSitios.cerrarRecursos();
			DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
			daoEspectaculos.setConn(conn);
			Espectaculo espectaculo = daoEspectaculos.darEspectaculoPorId(Integer.parseInt(rs.getString(4)));
			daoEspectaculos.cerrarRecursos();
			
			boolean realizada = rs.getString("REALIZADA") =="Y"?true:false;
			
			funcion = new Funcion(id, fecha, sitio, espectaculo, realizada);
		}
		return funcion;
	}

	public void registrarRelizacionFuncion(int idFuncion) throws SQLException
	{
		String sql = "UPDATE FUNCION SET REALIZADA = 'Y' ";
		sql += "WHERE ID =" + idFuncion;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public ArrayList<Funcion> darFuncionesDeCompaniaDeTeatro(String nombreCompania) throws NumberFormatException, Exception
	{
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();

		String sql = "SELECT * FROM "
				+ "(SELECT * FROM FUNCION FULL OUTER JOIN "
				+ "ESPECTACULO ON FUNCION.ESPECTACULO = ESPECTACULO.ID)T1 "
				+ "FULL OUTER (SELECT * FROM PATROCINA "
				+ "FULL OUTER JOIN COMPANIA ON PATROCINA.IDCOMPANIA = COMPANIA.ID)T2 "
				+ "ON T1.ID = T2.IDESPECTACULO WHERE T2.NOMBRE = " + nombreCompania;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString(1));
			Date fecha = rs.getDate(2);
			DAOTablaSitios daoSitios = new DAOTablaSitios();
			daoSitios.setConn(conn);
			Sitio sitio = daoSitios.darSitioPorId(Integer.parseInt(rs.getString(3)));
			DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
			daoEspectaculos.setConn(conn);
			Espectaculo espectaculo = daoEspectaculos.darEspectaculoPorId(Integer.parseInt(rs.getString(4)));
			boolean realizada = rs.getString("REALIZADA") =="Y"?true:false;
			funciones.add(new Funcion(id, fecha, sitio, espectaculo, realizada));
		}
		return funciones;
	}
}
