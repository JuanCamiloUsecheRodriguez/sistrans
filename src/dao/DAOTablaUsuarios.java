package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.BuenCliente;
import vos.Cliente;
import vos.ListaRFC11;
import vos.RFC11;
import vos.ReporteCompania;
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

	public String aux(String[] s){
		String r = "";
		for (int i = 0; i < s.length; i++) {
			r += "'"+s[i]+"'";
		}
		r.replace("''", "','");
		return r;
	}

	public List<RFC11> consultarCompras(int idGerente, String pfechaI, String pFechaF, String[] requerimientos,String localidad) throws Exception {

		ArrayList<RFC11> respuesta = new ArrayList<RFC11>();

		String sql = "SELECT ROL FROM USUARIO WHERE NUMDOCUMENTO = "+idGerente;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			if(rs.getString(1).equals("GERENTE")){
				String fechaMenor = pfechaI.replace("-", "/");
				String fechaMayor = pFechaF.replace("-", "/");

				String sql2 = "SELECT ESPECTACULO.NOMBRE as ESPECTACULONOMBRE,FUNCION.FECHA,SITIO.NOMBRE AS SITIONOMBRE,COUNT(BOLETA.ID) AS NUMBOLETASVENDIDAS,  SILLA,USUARIODOC,BOLETA.ID_LOCALIDAD,FUNCION,BOLETA.DEVUELTA FROM BOLETA"
						+ " INNER JOIN LOCALIDAD ON BOLETA.ID_LOCALIDAD = LOCALIDAD.ID_LOCALIDAD"
						+ " INNER JOIN FUNCION ON BOLETA.FUNCION = FUNCION.ID"
						+ " INNER JOIN SITIO ON SITIO.ID = FUNCION.SITIO"
						+ " INNER JOIN OFRECEREQUERIMIENTO ON SITIO.ID = OFRECEREQUERIMIENTO.IDSITIO"
						+ " INNER JOIN REQUERIMIENTO ON REQUERIMIENTO.ID = OFRECEREQUERIMIENTO.IDREQUERIMIENTO"
						+ " INNER JOIN ESPECTACULO ON FUNCION.ESPECTACULO = ESPECTACULO.ID"
						+ " WHERE FUNCION.FECHA BETWEEN TO_DATE('"+fechaMenor+"','DD/MM/RR') "
						+ " AND TO_DATE('"+fechaMayor+"','DD/MM/RR')"
						+ " AND REQUERIMIENTO.NOMBRE IN ("+aux(requerimientos)+")"
						+ " AND LOCALIDAD.NOMBRE = '"+localidad+"'"
						+ "GROUP BY ESPECTACULO.NOMBRE,FUNCION.FECHA,SITIO.NOMBRE,SILLA,USUARIODOC,BOLETA.ID_LOCALIDAD,FUNCION,BOLETA.DEVUELTA";

				System.out.println("SQL stmt:" + sql2);

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				ResultSet rs2 = prepStmt2.executeQuery();
				while(rs2.next())
				{
					String espectaculo = rs2.getString(1);
					Date fecha = rs2.getDate(2);
					String sitio = rs2.getString(3);
					int cantidadBoletas = rs2.getInt(4);

					respuesta.add(new RFC11(espectaculo, fecha, sitio, cantidadBoletas));
				}
			}
			else{
				throw new Exception("Esta operacion solo puede ser realizada por el gerente de FestivAndes.");
			}
		}
		return respuesta;

	}

	public List<BuenCliente> consultarBuenosClientes(int idGerente,int cantboletas) throws SQLException,Exception{
		ArrayList<BuenCliente> respuesta = new ArrayList<BuenCliente>();

		String sql = "SELECT ROL FROM USUARIO WHERE NUMDOCUMENTO = "+idGerente;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			if(rs.getString(1).equals("GERENTE")){

				String sql2 = "SELECT * FROM   (SELECT NUMDOCUMENTO,USUARIO.NOMBRE,EMAIL,ROL,USUARIO,COUNT(BOLETA.ID) AS NUMBOLETAS FROM USUARIO   "
						+ " INNER JOIN BOLETA ON USUARIO.NUMDOCUMENTO = BOLETA.USUARIODOC"
						+ " INNER JOIN LOCALIDAD ON BOLETA.ID_LOCALIDAD = LOCALIDAD.ID_LOCALIDAD"
						+ " WHERE LOCALIDAD.NOMBRE = 'PREFERENCIAL'"
						+ " GROUP BY NUMDOCUMENTO,USUARIO.NOMBRE,EMAIL,ROL,USUARIO,USUARIO.PASSWORD)"
						+ " WHERE NUMBOLETAS >="+cantboletas;

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				System.out.println("SQL stmt:" + sql2);
				ResultSet rs2 = prepStmt2.executeQuery();

				while(rs2.next()){
					int numDocumento = rs.getInt(1);
					String nombre = rs.getString(2);
					String email = rs.getString(3);
					String rol = rs.getString(4);
					String usuario = rs.getString(5);
					int numBoletas = rs.getInt(6);

					respuesta.add(new BuenCliente(numDocumento, nombre, email, rol, usuario, numBoletas));
				}

			}
			else{
				throw new Exception("Esta operacion solo puede ser realizada por el gerente de FestivAndes.");
			}

		}
		return respuesta;

	}
}
