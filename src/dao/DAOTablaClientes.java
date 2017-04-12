package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Abono;
import vos.Cliente;
import vos.NotaDebito;
import vos.Preferencia;
import vos.ReporteAsistencia;
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
		sql += pref.getCliente() + ",";
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

	public void addAbono(Abono abono) throws SQLException,Exception {

		String sql = "SELECT FECHAINICIO FROM FESTIVANDES";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			if(rs.getDate("FECHAINICIO").getTime() - System.currentTimeMillis() > 1814400000)
			{
				for (int i = 0; i < abono.getFunciones().size(); i++) {
					String sql2 = "INSERT INTO ABONO VALUES (SEC_ABONO.NEXTVAL,"
							+ abono.getCliente()+","
							+ abono.getFunciones().get(i)+")";

					System.out.println("SQL stmt:" + sql2);

					PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
					recursos.add(prepStmt2);
					prepStmt2.executeQuery();
				}

			}
			else
			{
				throw new Exception("Esta operacion solo se puede hacer 3 semanas antes del inicio de FestivAndes.");
			}
		}
	}

	public NotaDebito deleteAbono(int idCliente) throws SQLException,Exception {
		String sql = "SELECT FECHAINICIO FROM FESTIVANDES";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		NotaDebito nota = null;

		if(rs.next())
		{
			if(rs.getDate("FECHAINICIO").getTime() - System.currentTimeMillis() > 1814400000)
			{
				int valor = 0;
				String sqlAbono = "SELECT ID_FUNCION FROM ABONO WHERE CLIENTE = "+idCliente;

				System.out.println("SQL stmt:" + sqlAbono);

				PreparedStatement prepStmtA = conn.prepareStatement(sqlAbono);
				recursos.add(prepStmtA);
				ResultSet rsAbono = prepStmtA.executeQuery();

				while(rsAbono.next())
				{
					String sqlB = "SELECT PRECIO FROM BOLETA INNER JOIN LOCALIDAD ON LOCALIDAD.ID_LOCALIDAD = BOLETA.ID_LOCALIDAD WHERE BOLETA.USUARIODOC = "
							+ idCliente +" AND "
							+ "FUNCION = "+Integer.parseInt(rsAbono.getString("ID_FUNCION"));

					System.out.println("SQL stmt:" + sqlB);

					PreparedStatement prepStmtB = conn.prepareStatement(sqlB);
					recursos.add(prepStmtB);
					ResultSet rsBoleta = prepStmtB.executeQuery();
					if(rsBoleta.next())
					{
						valor += Integer.parseInt(rsBoleta.getString("PRECIO"));

						String sqlDelete = "DELETE FROM BOLETA WHERE BOLETA.USUARIODOC = "
								+ idCliente +" AND "
								+ "FUNCION = "+Integer.parseInt(rsAbono.getString("ID_FUNCION"));

						System.out.println("SQL stmt:" + sqlDelete);

						PreparedStatement prepStmtD = conn.prepareStatement(sqlDelete);
						recursos.add(prepStmtD);
						prepStmtD.executeQuery();
					}

				}
				String sqlDelete = "DELETE FROM ABONO WHERE CLIENTE ="+idCliente;

					System.out.println("SQL stmt:" + sqlDelete);

					PreparedStatement prepStmtD = conn.prepareStatement(sqlDelete);
					recursos.add(prepStmtD);
					prepStmtD.executeQuery();

				String sql2 = "INSERT INTO NOTADEBITO (ID_CLIENTE,VALOR) VALUES ("
						+idCliente+ ","
						+valor+")";

				System.out.println("SQL stmt:" + sql2);

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				prepStmt2.executeQuery();


				nota = new NotaDebito(idCliente, valor, false);

			}
			else
			{
				throw new Exception("Esta operación se puede hacer hasta tres (3) semanas antes del inicio de FestivAndes");
			}
		}
		return nota;
	}

	public ArrayList<ReporteAsistencia> darReporteAsistencia(int idUsuario) throws SQLException , Exception{
		ArrayList<ReporteAsistencia> reporte = new ArrayList<>();
		
		String sql = "SELECT ROL FROM USUARIO WHERE NUMDOCUMENTO = "+idUsuario;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			if(rs.getString(1).equals("ADMIN"))
			{
				String sqlAdmin = "SELECT IDCLIENTE, ID AS IDFUNCION, IDBOLETA, REALIZADA AS FUNCIONREALIZADA, DEVUELTA FROM "
						+ "(SELECT USUARIODOC AS IDCLIENTE, FUNCION, ID AS IDBOLETA, DEVUELTA FROM BOLETA)T1 "
						+ "INNER JOIN FUNCION ON T1.FUNCION = FUNCION.ID ORDER BY DEVUELTA,FUNCIONREALIZADA DESC";

				System.out.println("SQL stmt:" + sqlAdmin);

				PreparedStatement prepStmtAdmin = conn.prepareStatement(sqlAdmin);
				recursos.add(prepStmtAdmin);
				ResultSet rsAdmin = prepStmtAdmin.executeQuery();
				while(rsAdmin.next())
				{
					int idCliente = Integer.parseInt(rsAdmin.getString(1));
					int idFuncion = Integer.parseInt(rsAdmin.getString(2));
					int idBoleta = Integer.parseInt(rsAdmin.getString(3));
					boolean funcionRealizada = rsAdmin.getString(4).equals("Y") ? true: false;
					boolean devuelta= rsAdmin.getString(5).equals("Y") ? true: false;
					
					reporte.add(new ReporteAsistencia(idCliente, idFuncion, idBoleta, funcionRealizada, devuelta));
				}
			}
			else if(rs.getString(1).equals("USUARIO"))
			{
				String sqlUsuario = "SELECT IDCLIENTE, ID AS IDFUNCION, IDBOLETA, REALIZADA AS FUNCIONREALIZADA, DEVUELTA FROM "
						+ "(SELECT USUARIODOC AS IDCLIENTE, FUNCION, ID AS IDBOLETA, DEVUELTA FROM BOLETA)T1 "
						+ "INNER JOIN FUNCION ON T1.FUNCION = FUNCION.ID "
						+ "WHERE IDCLIENTE = "+idUsuario+" ORDER BY DEVUELTA,FUNCIONREALIZADA DESC";

				System.out.println("SQL stmt:" + sqlUsuario);

				PreparedStatement prepStmtUsuario = conn.prepareStatement(sqlUsuario);
				recursos.add(prepStmtUsuario);
				ResultSet rsUsuario= prepStmtUsuario.executeQuery();
				while(rsUsuario.next())
				{
					int idCliente = Integer.parseInt(rsUsuario.getString(1));
					int idFuncion = Integer.parseInt(rsUsuario.getString(2));
					int idBoleta = Integer.parseInt(rsUsuario.getString(3));
					boolean funcionRealizada = rsUsuario.getString(4).equals("Y") ? true: false;
					boolean devuelta= rsUsuario.getString(5).equals("Y") ? true: false;
					
					reporte.add(new ReporteAsistencia(idCliente, idFuncion, idBoleta, funcionRealizada, devuelta));
				}
			}
			else{
				throw new Exception("Consulta no permitida para representantes");
			}
		}
		else
		{
			throw new Exception("Usuario no registrado en el sistema.");
		}
		
		return reporte;
	}

}
