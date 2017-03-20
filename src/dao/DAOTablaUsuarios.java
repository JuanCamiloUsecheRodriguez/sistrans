package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Usuario;

public class DAOTablaUsuarios {

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
	public DAOTablaUsuarios() {
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
	
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM USUARIO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int numDocumento = Integer.parseInt(rs.getString("NUMDOCUMENTO"));
			String nombre = rs.getString("NOMBRE");
			String email = rs.getString("EMAIL");
			String rol = rs.getString("ROL");
			String login = rs.getString("USUARIO");
			String password = rs.getString("PASSWORD");
			usuarios.add(new Usuario(numDocumento, nombre, email, rol, login, password));
		}
		return usuarios;
	}
	
	public Usuario darUsuariosPorId(int pId) throws SQLException, Exception {
		Usuario usuario = null;

		String sql = "SELECT * FROM USUARIO WHERE NUMDOCUMENTO ='"+pId+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			int numDocumento = Integer.parseInt(rs.getString("NUMDOCUMENTO"));
			String nombre = rs.getString("NOMBRE");
			String email = rs.getString("EMAIL");
			String rol = rs.getString("ROL");
			String login = rs.getString("USUARIO");
			String password = rs.getString("PASSWORD");
			usuario = new Usuario(numDocumento, nombre, email, rol, login, password);
		}
		return usuario;
	}
	
}
