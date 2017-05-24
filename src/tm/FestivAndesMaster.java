package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

import dao.DAOTablaBoletas;
import dao.DAOTablaClientes;
import dao.DAOTablaEspectaculos;
import dao.DAOTablaFunciones;
import dao.DAOTablaSitios;
import dao.DAOTablaUsuarios;
import dao.DAOTablaVideos;
import dtm.IncompleteReplyException;
import dtm.JMSFunciones;
import dtm.NonReplyException;
import vos.Abono;
import vos.Boleta;
import vos.BoletaDetail;
import vos.BuenCliente;
import vos.CompraBoletas;
import vos.Funcion;
import vos.ListaBoletas;
import vos.ListaBuenCliente;
import vos.ListaFunciones;
import vos.ListaNotas;
import vos.ListaRFC11;
import vos.ListaReporteAsistencia;
import vos.ListaReporteCompania;
import vos.ListaReporteEspectaculo;
import vos.ListaReporteFuncion;
import vos.ListaSitios;
import vos.ListaUsuarios;
import vos.ListaVideos;
import vos.NotaDebito;
import vos.Preferencia;
import vos.RFC11;
import vos.ReporteAsistencia;
import vos.ReporteCompania;
import vos.ReporteEspectaculo;
import vos.ReporteFuncion;
import vos.Sitio;
import vos.SuperSitio;
import vos.Usuario;
import vos.Video;

public class FestivAndesMaster {

	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	public static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private  String conn1ectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user1;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password1;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url1;
	
	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user2;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password2;
	
	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user3;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password3;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Conexión a la base de datos
	 */
	private Connection conn1;
	
	private Connection conn2;
	
	private Connection conn3;
	
	private String myQueue;


	private int numberApps;

	private String topicAllFunciones;


	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public FestivAndesMaster(String contextPathP) {
		conn1ectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.conn1ectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url1 = prop.getProperty("url");
			this.user1 = prop.getProperty("usuario1");
			this.password1 = prop.getProperty("clave1");
			this.user2 = prop.getProperty("usuario2");
			this.password2 = prop.getProperty("clave2");
			this.user3 = prop.getProperty("usuario3");
			this.password3 = prop.getProperty("clave3");
			this.driver = prop.getProperty("driver");
			this.myQueue = prop.getProperty("myQueue");
			this.topicAllFunciones = prop.getProperty("topicAllFunciones");
			this.numberApps = Integer.parseInt(prop.getProperty("numberApps"));
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
	 */
	private Connection darConexion1() throws SQLException {
		System.out.println("Connecting to DataSource1: " + url1 + " With user: " + user1);
		return DriverManager.getConnection(url1, user1, password1);
	}
	
	private Connection darConexion2() throws SQLException {
		System.out.println("Connecting to DataSource1: " + url1 + " With user: " + user2);
		return DriverManager.getConnection(url1, user2, password2);
	}
	
	private Connection darConexion3() throws SQLException {
		System.out.println("Connecting to DataSource1: " + url1 + " With user: " + user3);
		return DriverManager.getConnection(url1, user3, password3);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaUsuarios darUsuariosLocal() throws Exception {
		ArrayList<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoUsuarios.setConn(conn1);
			usuarios = daoUsuarios.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaUsuarios(usuarios);
	}
	
	public ListaFunciones darFuncionesRemote() throws Exception {
		ListaFunciones funciones;
		DAOTablaFunciones dao = new DAOTablaFunciones();
		ArrayList<Funcion> funcionesLocal = new ArrayList<Funcion>();
		try {	
			Connection conn1 = darConexion1();
			dao.setConn(conn1);
			funcionesLocal = dao.darFunciones();
			
			JMSFunciones instancia = JMSFunciones.darInstacia(this);
			instancia.setUpJMSManager(this.numberApps, this.myQueue, this.topicAllFunciones);
			funciones = instancia.getFuncionesResponse();  
			
			funciones.addFunciones(new ListaFunciones(funcionesLocal));
			System.out.println("size:" + funciones.getFunciones().size());
		} catch (NonReplyException e) {
			throw new IncompleteReplyException("No Reply from apps - Local Videos:",new ListaFunciones(funcionesLocal));
		} catch (IncompleteReplyException e) {
			ListaFunciones temp = e.getPartialResponseFunciones();
			temp.addFunciones(new ListaFunciones(funcionesLocal));
			throw new IncompleteReplyException("Incomplete Reply:",temp);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return funciones; 

	}


	public void actualizarPreferencia( int idCategoriaAnterior, Preferencia pref) throws Exception {
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoClientes.setConn(conn1);
			daoClientes.actualizarPreferenciaCliente(idCategoriaAnterior, pref);
			conn1.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deletePreferencia(Preferencia pref) throws Exception {
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoClientes.setConn(conn1);
			daoClientes.eliminarPreferenciaCliente(pref);
			conn1.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public boolean verificarCompra(String localidad, int funcion) throws SQLException, Exception
	{
		boolean r = false;
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			conn1.setAutoCommit(false);
			daoBoletas.setConn(conn1);
			r = daoBoletas.verificarCompra(localidad, funcion);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return r;
	}

	public void addBoletas(CompraBoletas lista) throws Exception {
		DAOTablaBoletas daoTablaBoletas = new DAOTablaBoletas();
		this.conn1 = darConexion1();
		daoTablaBoletas.setConn(conn1);
		conn1.setAutoCommit(false);
		System.out.println("El AUTOCOMMIT ES: "+conn1.getAutoCommit());
		Savepoint s =  conn1.setSavepoint("comprarBoletas");
		List<Boleta> boletas = lista.getBoletas();
		try 
		{
			daoTablaBoletas.addBoletas(boletas);
			System.out.println("El AUTOCOMMIT2 ES: "+conn1.getAutoCommit());
			conn1.commit();

		} catch (SQLException e) {
			conn1.rollback(s);
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn1.rollback(s);
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaBoletas.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public ListaBoletas darBoletasPorId(int idBoleta) throws Exception {
		ArrayList<BoletaDetail> boletas;
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoBoletas.setConn(conn1);
			boletas = daoBoletas.darBoletasPorId(idBoleta);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBoletas(boletas);
	}

	public ListaBoletas darBoletas() throws Exception {
		ArrayList<BoletaDetail> boletas;
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoBoletas.setConn(conn1);
			boletas = daoBoletas.darBoletas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBoletas(boletas);
	}

	public ListaSitios darSitios() throws Exception {
		ArrayList<Sitio> sitios;
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoSitios.setConn(conn1);
			sitios = daoSitios.darSitios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSitios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSitios(sitios);
	}

	public Sitio darSitiosPorId(int idSitio) throws Exception {
		Sitio sitio = null;
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoSitios.setConn(conn1);
			sitio = daoSitios.darSitioPorId(idSitio);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSitios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return sitio;
	}


	public void registrarRealizacionFuncion(int idFuncion) throws Exception {
		DAOTablaFunciones daoTablaFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoTablaFunciones.setConn(conn1);
			daoTablaFunciones.registrarRelizacionFuncion(idFuncion);;
			conn1.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	
	

	public ListaFunciones darFuncionesLocal() throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			funciones = daoFunciones.darFunciones();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}
	
	public ListaFunciones darFuncionesRangoFecha(String fechaInicial, String fechaFin, String order) throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			funciones = daoFunciones.darFuncionesEntreRangoDeFechas(fechaInicial, fechaFin, order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	public ListaFunciones darFuncionesCompania(String compania, String order) throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			funciones = daoFunciones.darFuncionesDeCompaniaDeTeatro(compania, order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	public ListaFunciones darFuncionesCategoria(String categoria, String order) throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			funciones = daoFunciones.darFuncionesPorCategoriaDeEspectaculo(categoria, order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	public ListaFunciones darFuncionesIdioma(String idioma, String order) throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			funciones = daoFunciones.darFuncionesPorIdioma(idioma, order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	public ListaFunciones darFuncionesAccesibilidad(String accesibilidad, String order) throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			funciones = daoFunciones.darFuncionesPorAccesibilidad(accesibilidad, order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	public SuperSitio darSitiosCompleto(int idSitio, String order) throws Exception {
		SuperSitio sitio;
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoSitios.setConn(conn1);
			sitio = daoSitios.consultarSitio(idSitio,order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSitios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return sitio;
	}

	public ListaReporteFuncion darReporteFuncion(int idFuncion, String order) throws Exception {
		ArrayList<ReporteFuncion> reportes;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			reportes = daoFunciones.darReporteFuncion(idFuncion,order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReporteFuncion(reportes);
	}

	public ListaReporteEspectaculo darReporteEspectaculo(int idEspectaculo, String order) throws Exception {
		ArrayList<ReporteEspectaculo> reportes;
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
		try{
			//////Transacción
			this.conn1 = darConexion1();
			daoEspectaculos.setConn(conn1);
			reportes = daoEspectaculos.darReporteEspectaculo(idEspectaculo,order);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculos.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReporteEspectaculo(reportes);
	}

	public void addAbono(Abono abono) throws SQLException,Exception {
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		this.conn1 = darConexion1();
		daoClientes.setConn(conn1);
		conn1.setAutoCommit(false);
		Savepoint s = conn1.setSavepoint("ComprarAbono");
		try 
		{
			//////Transacción
			daoClientes.addAbono(abono);
			conn1.commit();

		} catch (SQLException e) {
			conn1.rollback(s);
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn1.rollback(s);
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		
	}
	
	public boolean deleteCompania2pc(int idCompania) throws SQLException, Exception {
		try{
			InitialContext ctx = new InitialContext();
			UserTransaction utx = (UserTransaction) ctx.lookup("/UserTransaction");
			
			try {
				
				this.conn1 = darConexion1();
				this.conn2 = darConexion2();
				this.conn3 = darConexion3();
				utx.begin();
				try {
					Statement st = conn1.createStatement();
					String sql = "DELETE FROM BOLETA WHERE FUNCION IN (SELECT IDFUNCION FROM "
							+ "(SELECT FUNCION.ID AS IDFUNCION,FUNCION.ESPECTACULO FROM FUNCION "
							+ "INNER JOIN ESPECTACULO ON FUNCION.ESPECTACULO = ESPECTACULO.ID)T1 "
							+ "INNER JOIN PATROCINA ON T1.ESPECTACULO = PATROCINA.IDESPECTACULO WHERE IDCOMPANIA ="+idCompania + ")";
				System.out.println(sql);
				st.executeQuery(sql);
				sql = "DELETE FROM FUNCION WHERE ID IN (SELECT IDFUNCION FROM "
						+ "(SELECT FUNCION.ID AS IDFUNCION,FUNCION.ESPECTACULO FROM FUNCION "
						+ "INNER JOIN ESPECTACULO ON FUNCION.ESPECTACULO = ESPECTACULO.ID)T1 "
						+ "INNER JOIN PATROCINA ON T1.ESPECTACULO = PATROCINA.IDESPECTACULO WHERE IDCOMPANIA ="+ idCompania +")";
				st.executeQuery(sql);
				sql = "DELETE FROM COMPANIA WHERE ID = "+ idCompania;
				st.executeQuery(sql);
				System.out.println("OK Datasource 1");
				st.close();
				
				} catch (Exception e) {
					// TODO: handle exception
					utx.setRollbackOnly();
				}
				try {
					Statement st = conn2.createStatement();
					String sql = "DELETE FROM BOLETA WHERE IDBOLETA IN "
							+ "(SELECT DISTINCT IDBOLETA FROM BOLETA NATURAL"
							+ " JOIN FUNCION NATURAL JOIN OBRA NATURAL JOIN "
							+ "FUNCION NATURAL JOIN COMPANIAOBRA NATURAL JOIN "
							+ "COMPANIA WHERE IDCOMPANIA = "+ idCompania+ ")";
				System.out.println(sql);
				st.executeQuery(sql);
				sql = "UPDATE FUNCION SET REALIZADO = 2 WHERE IDFUNCION IN "
						+ "(SELECT DISTINCT IDFUNCION FROM FUNCION NATURAL JOIN "
						+ "OBRA NATURAL JOIN FUNCION NATURAL JOIN COMPANIAOBRA NATURAL JOIN "
						+ "COMPANIA WHERE IDCOMPANIA = "+ idCompania +")";
				st.executeQuery(sql);
				sql = "DELETE FROM COMPANIA WHERE ID = "+idCompania;
				st.executeQuery(sql);
				System.out.println("OK Datasource 2");
				st.close();
				
				} catch (Exception e) {
					// TODO: handle exception
					utx.setRollbackOnly();
				}
				try {
					Statement st = conn3.createStatement();
					String sql = "DELETE FROM BOLETA WHERE FUNCION IN (SELECT IDFUNCION FROM "
							+ "(SELECT FUNCION.ID AS IDFUNCION,FUNCION.ESPECTACULO FROM FUNCION "
							+ "INNER JOIN ESPECTACULO ON FUNCION.ESPECTACULO = ESPECTACULO.ID)T1 "
							+ "INNER JOIN PATROCINA ON T1.ESPECTACULO = PATROCINA.IDESPECTACULO WHERE IDCOMPANIA ="+idCompania + ")";
				System.out.println(sql);
				st.executeQuery(sql);
				sql = "DELETE FROM FUNCION WHERE ID IN (SELECT IDFUNCION FROM "
						+ "(SELECT FUNCION.ID AS IDFUNCION,FUNCION.ESPECTACULO FROM FUNCION "
						+ "INNER JOIN ESPECTACULO ON FUNCION.ESPECTACULO = ESPECTACULO.ID)T1 "
						+ "INNER JOIN PATROCINA ON T1.ESPECTACULO = PATROCINA.IDESPECTACULO WHERE IDCOMPANIA ="+ idCompania +")";
				st.executeQuery(sql);
				sql = "DELETE FROM COMPANIA WHERE ID = "+idCompania;
				st.executeQuery(sql);
				System.out.println("OK Datasource 3");
				st.close();
				
				} catch (Exception e) {
					// TODO: handle exception
					utx.setRollbackOnly();
				}
				utx.commit();
				conn1.close();
				conn2.close();
				conn3.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return true;
	}

	public NotaDebito deleteBoleta(int idBoleta) throws SQLException, Exception {
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		this.conn1 = darConexion1();
		daoBoletas.setConn(conn1);
		conn1.setAutoCommit(false);
		Savepoint s = conn1.setSavepoint("deleteBoleta");
		NotaDebito r = null;
		try 
		{
			//////Transacción
			r = daoBoletas.deleteBoleta(idBoleta);
			conn1.commit();

		} catch (SQLException e) {
			conn1.rollback(s);
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn1.rollback(s);
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return r;
	}

	public NotaDebito deleteAbono(int idCliente) throws SQLException,Exception {
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		this.conn1 = darConexion1();
		daoClientes.setConn(conn1);
		conn1.setAutoCommit(false);
		Savepoint s = conn1.setSavepoint("deleteBoleta");
		NotaDebito r = null;
		try 
		{
			//////Transacción
			r = daoClientes.deleteAbono(idCliente);
			conn1.commit();

		} catch (SQLException e) {
			conn1.rollback(s);
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn1.rollback(s);
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return r;
	}
	
	public ListaNotas generarNotas(List<Integer> boletas) throws SQLException, Exception{
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		this.conn1 = darConexion1();
		daoBoletas.setConn(conn1);
		conn1.setAutoCommit(false);
		Savepoint s = conn1.setSavepoint("notaDebito");
		List<NotaDebito> r = new ArrayList<NotaDebito>();
		try 
		{
			//////Transacción
			for (int i = 0; i < boletas.size(); i++) {
				r.add(daoBoletas.addNotasDebito(boletas.get(i)));
				conn1.commit();
			}
			
			
			

		} catch (SQLException e) {
			conn1.rollback(s);
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn1.rollback(s);
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaNotas(r);
	}

	public void deleteCompaniaLocal(int idCompania) throws Exception{
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		this.conn = darConexion1();
		daoFunciones.setConn(conn);
		conn.setAutoCommit(false);
		Savepoint s = conn.setSavepoint("deleteCompania");
		ListaNotas r = null;
		List<Funcion> funciones = null;
		try 
		{
			//////Transacción
			funciones = daoFunciones.darFunciones();
			for(int i = 0; i < funciones.size(); i++)
			{
				deleteFuncion(i);
			}

		} catch (SQLException e) {
			conn.rollback(s);
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback(s);
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public ListaNotas deleteFuncion(int idFuncion) throws SQLException, Exception{
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		this.conn1 = darConexion1();
		daoFunciones.setConn(conn1);
		conn1.setAutoCommit(false);
		Savepoint s = conn1.setSavepoint("deleteBoleta");
		ListaNotas r = null;
		List<Integer> boletas = null;
		try 
		{
			//////Transacción
			boletas = daoFunciones.deleteFuncion(idFuncion);
			
			conn1.commit();
			
			r = generarNotas(boletas);

		} catch (SQLException e) {
			conn1.rollback(s);
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn1.rollback(s);
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return r;
	}

	public ListaReporteAsistencia darReporteAsistencia(int idUsuario)throws SQLException , Exception {
		ArrayList<ReporteAsistencia> reportes;
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoClientes.setConn(conn1);
			conn1.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			reportes = daoClientes.darReporteAsistencia(idUsuario);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReporteAsistencia(reportes);
	}

	public ListaReporteCompania darReporteCompaniaLocal() throws SQLException , Exception{
		ArrayList<ReporteCompania> reportes;
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoClientes.setConn(conn1);
			conn1.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			reportes = daoClientes.darReporteCompania();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReporteCompania(reportes);
	}

	public ListaUsuarios darAsistenciaRFC9(int companiaId,String fechaI, String fechaF) throws Exception {
		List<Usuario> usuarios;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			usuarios = daoFunciones.consultarAsistenciaRFC9(companiaId, fechaI, fechaF);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaUsuarios(usuarios);
	}
	
	public ListaUsuarios darAsistenciaRFC10(int companiaId,String fechaI, String fechaF) throws Exception {
		List<Usuario> usuarios;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			usuarios = daoFunciones.consultarAsistenciaRFC10(companiaId, fechaI, fechaF);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaUsuarios(usuarios);
	}
	
	public ListaRFC11 consultarComprasRFC11(int idGerente,String pfechaI,String pfechaF, String[] requerimientos,String localidad ) throws Exception {
		List<RFC11> reporte;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoUsuarios.setConn(conn1);
			reporte = daoUsuarios.consultarCompras(idGerente, pfechaI, pfechaF, requerimientos, localidad);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaRFC11(reporte);
	}
	
	public ListaBuenCliente consultarBuenosClientes(int idGerente,int cantboletas ) throws Exception {
		List<BuenCliente> reporte;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoUsuarios.setConn(conn1);
			reporte = daoUsuarios.consultarBuenosClientes(idGerente, cantboletas);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaBuenCliente(reporte);
	}
	
	public void generarUsuarios(int inicial,int cant) throws SQLException{
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoUsuarios.setConn(conn1);
			daoUsuarios.generarDatos(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarClientes(int inicial,int cant) throws SQLException{
		DAOTablaClientes daoClientes = new DAOTablaClientes();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoClientes.setConn(conn1);
			daoClientes.generarDatos(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoClientes.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarSitios(int inicial,int cant) throws SQLException{
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoSitios.setConn(conn1);
			daoSitios.generarDatos(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSitios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarFunciones(int inicial,int cant) throws SQLException{
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoFunciones.setConn(conn1);
			daoFunciones.generarDatos(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarCompania(int inicial,int cant) throws SQLException{
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoEspectaculos.setConn(conn1);
			daoEspectaculos.generarCompanias(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculos.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarPatrocina(int inicialCompanias,int inicialEspectaculos,int cant) throws SQLException{
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoEspectaculos.setConn(conn1);
			daoEspectaculos.generarPatrocinas(inicialCompanias,inicialEspectaculos, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculos.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarEspectaculo(int inicial,int cant) throws SQLException{
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoEspectaculos.setConn(conn1);
			daoEspectaculos.generarEspectaculo(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculos.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarBoletas(int inicial,int cant) throws SQLException{
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoBoletas.setConn(conn1);
			daoBoletas.generarDatos(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarLocalidades(int inicial,int cant) throws SQLException{
		DAOTablaSitios daoTablaSitios = new DAOTablaSitios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoTablaSitios.setConn(conn1);
			daoTablaSitios.generarLocalidad(inicial,cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaSitios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void generarRequerimientos(int inicial,int cant) throws SQLException{
		DAOTablaSitios daoTablaSitios = new DAOTablaSitios();
		try 
		{
			//////Transacción
			this.conn1 = darConexion1();
			daoTablaSitios.setConn(conn1);
			daoTablaSitios.generarOfreceReq(inicial, cant);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoTablaSitios.cerrarRecursos();
				if(this.conn1!=null)
					this.conn1.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
}
