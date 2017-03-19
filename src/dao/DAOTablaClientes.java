package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Preferencia;
import vos.Usuario;
import vos.Video;

public class DAOTablaClientes {
	

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOCliente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaClientes() {
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
	 * Metodo que registra una nueva preferencia para un cliente
	 * @param pIdCliente
	 * @param pIdPreferencia
	 * @throws SQLException 
	 */
	public void registrarPreferenciaCliente(Preferencia pref) throws SQLException
	{
		String sql = "INSERT INTO PREFIEREN VALUES (";
		sql += pref.getCliente() + ",'";
		sql += pref.getCategoria() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void actualizarPreferenciaCliente(int pIdCategoriaAnterior,Preferencia pref) throws SQLException
	{
		String sql = "UPDATE PREFIEREN SET CATEGORIA=";
		sql += pref.getCategoria() + "WHERE ID_CLIENTE="
				+ pref.getCliente() +"AND CATEGORIA = "+pIdCategoriaAnterior;
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void eliminarPreferenciaCliente(Preferencia pref) throws SQLException
	{
		String sql = "DELETE FROM PREFIEREN WHERE ID_CLIENTE=";
		sql += pref.getCliente() + "AND CATEGORIA="
				+ pref.getCategoria();
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}
