package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Boleta;
import vos.Funcion;
import vos.Usuario;
import vos.Boleta;

public class DAOTablaBoletas {
	

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOBoleta
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaBoletas() {
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
	 * Método que, usando la conexión a la base de datos, saca todos las boletas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM BOLETAS;
	 * @return Arraylist con los Boletas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Boleta> darBoletas() throws SQLException, Exception {
		ArrayList<Boleta> Boletas = new ArrayList<Boleta>();

		String sql = "SELECT * FROM BOLETA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String silla = rs.getString("SILLA");
			int precio = Integer.parseInt(rs.getString("PRECIO"));
			int cupos = Integer.parseInt(rs.getString("CUPOS"));
			String localidad = rs.getString("LOCALIDAD");
			
			int idFuncion = Integer.parseInt(rs.getString("FUNCION"));
			DAOTablaFunciones daoTablaFunciones = new DAOTablaFunciones();
			Funcion funcion = daoTablaFunciones.darFuncionPorId(idFuncion);
			
			int numDocumentoUsuario = Integer.parseInt(rs.getString("USUARIODOC"));
			DAOTablaUsuarios daoTablaUsuarios = new DAOTablaUsuarios();
			Usuario usuario = daoTablaUsuarios.darUsuariosPorId(numDocumentoUsuario);
			
			Boletas.add(new Boleta(id, silla, precio, cupos, localidad, usuario, funcion));
		}
		return Boletas;
	}


	/**
	 * Método que busca el/los Boletas con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los Boletas a buscar
	 * @return ArrayList con los Boletas encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Boleta> buscarBoletasPorId(Integer pId) throws SQLException, Exception {
		ArrayList<Boleta> Boletas = new ArrayList<Boleta>();

		String sql = "SELECT * FROM BOLETA WHERE ID ='" + pId + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String silla = rs.getString("SILLA");
			int precio = Integer.parseInt(rs.getString("PRECIO"));
			int cupos = Integer.parseInt(rs.getString("CUPOS"));
			String localidad = rs.getString("LOCALIDAD");
			
			int idEspectaculo = Integer.parseInt(rs.getString("ESPECTACULO"));
			
			DAOTablaFunciones daoTablaFunciones = new DAOTablaFunciones();
			Funcion funcion = daoTablaFunciones.darFuncionPorId(idEspectaculo);
			
			int numDocumentoUsuario = Integer.parseInt(rs.getString("CLIENTEDOC"));
			
			DAOTablaUsuarios daoTablaUsuarios = new DAOTablaUsuarios();
			Usuario usuario = daoTablaUsuarios.darUsuariosPorId(numDocumentoUsuario);
			
			Boletas.add(new Boleta(id, silla, precio, cupos, localidad, usuario, funcion));
		}

		return Boletas;
	}

	/**
	 * Método que agrega el Boleta que entra como parámetro a la base de datos.
	 * @param Boleta - el Boleta a agregar. Boleta !=  null
	 * <b> post: </b> se ha agregado el Boleta a la base de datos en la transaction actual. pendiente que el Boleta master
	 * haga commit para que el Boleta baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Boleta a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addBoleta(Boleta Boleta) throws SQLException, Exception {

		String sql = "INSERT INTO BOLETA VALUES (";
		sql += Boleta.getId() + ",'";
		sql += Boleta.getSilla()+ "',";
		sql += Boleta.getPrecio()+ "',";
		sql += Boleta.getCupos()+ "',";
		sql += Boleta.getLocalidad()+ "',";
		sql += Boleta.getFuncion().getId()+ "',";
		sql += Boleta.getUsario().getNumDocumento() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
}
