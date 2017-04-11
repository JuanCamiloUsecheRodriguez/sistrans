package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.Boleta;
import vos.BoletaDetail;
import vos.Funcion;
import vos.NotaDebito;
import vos.Usuario;
import vos.BoletaDetail;

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
	public ArrayList<BoletaDetail> darBoletas() throws SQLException, Exception {
		ArrayList<BoletaDetail> Boletas = new ArrayList<BoletaDetail>();

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
			daoTablaFunciones.setConn(conn);
			Funcion funcion = daoTablaFunciones.darFuncionPorId(idFuncion);
			daoTablaFunciones.cerrarRecursos();
			int numDocumentoUsuario = Integer.parseInt(rs.getString("USUARIODOC"));
			DAOTablaUsuarios daoTablaUsuarios = new DAOTablaUsuarios();
			daoTablaUsuarios.setConn(conn);
			Usuario usuario = daoTablaUsuarios.darUsuariosPorId(numDocumentoUsuario);
			daoTablaFunciones.cerrarRecursos();
			Boletas.add(new BoletaDetail(id, silla, precio, cupos, localidad, usuario, funcion));
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
	public ArrayList<BoletaDetail> darBoletasPorId(Integer pId) throws SQLException, Exception {
		ArrayList<BoletaDetail> Boletas = new ArrayList<BoletaDetail>();

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
			int idFuncion = Integer.parseInt(rs.getString("FUNCION"));
			DAOTablaFunciones daoTablaFunciones = new DAOTablaFunciones();
			daoTablaFunciones.setConn(conn);
			Funcion funcion = daoTablaFunciones.darFuncionPorId(idFuncion);
			daoTablaFunciones.cerrarRecursos();
			int numDocumentoUsuario = Integer.parseInt(rs.getString("USUARIODOC"));
			DAOTablaUsuarios daoTablaUsuarios = new DAOTablaUsuarios();
			daoTablaUsuarios.setConn(conn);
			Usuario usuario = daoTablaUsuarios.darUsuariosPorId(numDocumentoUsuario);
			daoTablaFunciones.cerrarRecursos();
			Boletas.add(new BoletaDetail(id, silla, precio, cupos, localidad, usuario, funcion));
		}

		return Boletas;
	}

	public boolean verificarCompra(String localidad, int funcion) throws Exception{
		String sql = "SELECT NUMERADA FROM LOCALIDAD WHERE "
				+ "ID_LOCALIDAD = '" + localidad+"' AND "
				+ "FK_IDFUNCION = "+ funcion ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next())
		{
			String r = rs.getString("NUMERADA");
			if (r.equals("Y")) {
				return true;				
			}
			else
			{
				return false;
			}
		}
		else
		{
			throw new Exception("no existe esa localidad para esa funcion");
		}



	}

	/**
	 * Método que agrega el Boleta que entra como parámetro a la base de datos.
	 * @param Boleta - el Boleta a agregar. Boleta !=  null
	 * <b> post: </b> se ha agregado el Boleta a la base de datos en la transaction actual. pendiente que el Boleta master
	 * haga commit para que el Boleta baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Boleta a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addBoletas(List<Boleta> boletas) throws SQLException, Exception {

		String sql = "SELECT NUMERADA FROM LOCALIDAD WHERE "
				+ "ID_LOCALIDAD = '" + boletas.get(0).getLocalidad()+"' AND "
				+ "FK_IDFUNCION = "+ boletas.get(0).getFuncion() ;
		String loc = boletas.get(0).getLocalidad();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			if(rs.getString("NUMERADA").equals("Y"))
			{
				int numAnterior = boletas.get(0).getSilla()-1;
				for (int i = 0; i < boletas.size() ; i++) {
					if(numAnterior==(boletas.get(i).getSilla())-1 && boletas.get(i).getLocalidad().equals(loc))
					{
						sql = "INSERT INTO BOLETA VALUES (SEC_BOLETA.NEXTVAL,";
						sql += boletas.get(i).getSilla()+ ",";
						sql += boletas.get(i).getUsario() + ",'";
						sql += boletas.get(i).getLocalidad()+ "',";
						sql += boletas.get(i).getFuncion()+ ")";


						System.out.println("SQL stmt:" + sql);

						PreparedStatement prepStmt2 = conn.prepareStatement(sql);
						recursos.add(prepStmt2);
						prepStmt2.executeQuery();

						sql = "UPDATE LOCALIDAD SET CUPOSDISPONIBLES = CUPOSDISPONIBLES-1 WHERE "
								+ "ID_LOCALIDAD = '" + boletas.get(0).getLocalidad()+"' AND "
								+ "FK_IDFUNCION = "+ boletas.get(0).getFuncion() ;
						System.out.println("SQL stmt:" + sql);

						PreparedStatement prepStmt3 = conn.prepareStatement(sql);
						recursos.add(prepStmt3);
						prepStmt3.executeQuery();

						numAnterior = boletas.get(i).getSilla();
					}
					else{
						throw new Exception("las sillas no son contiguas");
					}
				}
			}
			else
			{
				for (int i = 0; i < boletas.size() ; i++) {

					sql = "INSERT INTO BOLETA VALUES (SEC_BOLETA.NEXTVAL,";
					sql += boletas.get(i).getSilla()+ ",";
					sql += boletas.get(i).getUsario() + ",'";
					sql += boletas.get(i).getLocalidad()+ "',";
					sql += boletas.get(i).getFuncion()+ ")";

					System.out.println("SQL stmt:" + sql);

					PreparedStatement prepStmt2 = conn.prepareStatement(sql);
					recursos.add(prepStmt2);
					prepStmt2.executeQuery();

				}
			}

		}	

	}

	public NotaDebito deleteBoleta(int idBoleta) throws SQLException,Exception {
		String sql = "SELECT FECHA FROM FUNCION WHERE ID = (SELECT FUNCION FROM BOLETA WHERE ID = "
				+ idBoleta +")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		NotaDebito nota = null;

		if(rs.next())
		{
			if(rs.getDate("FECHA").getTime() - System.currentTimeMillis() > 432000000)
			{
				String sql2 = "INSERT INTO NOTADEBITO (ID_CLIENTE,VALOR) VALUES ("
						+ "(SELECT USUARIODOC FROM BOLETA WHERE ID = "+idBoleta+"),"
						+ "(SELECT PRECIO FROM BOLETA INNER JOIN LOCALIDAD ON LOCALIDAD.ID_LOCALIDAD = BOLETA.ID_LOCALIDAD WHERE ID ="
						+idBoleta+")) ";

				System.out.println("SQL stmt:" + sql2);

				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				recursos.add(prepStmt2);
				prepStmt2.executeQuery();

				String sql4 = "SELECT * FROM NOTADEBITO WHERE ID_CLIENTE = (SELECT USUARIODOC FROM BOLETA WHERE ID = "+idBoleta+")";

				System.out.println("SQL stmt:" + sql4);

				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				ResultSet r2 =prepStmt4.executeQuery();
				if(r2.next())
				{
					int cliente = Integer.parseInt(r2.getString(1));
					int valor = Integer.parseInt(r2.getString(2));
					boolean reclamada = r2.getString(3).equals("Y") ? true : false;
					nota = new NotaDebito(cliente, valor, reclamada);
				}

				String sql3 = "DELETE FROM BOLETA WHERE ID = "
						+ idBoleta;

				System.out.println("SQL stmt:" + sql3);

				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();

			}
			else
			{
				throw new Exception("Esta operación se puede hacer hasta cinco (5) días antes de la función");
			}
		}
		return nota;
	}


}
