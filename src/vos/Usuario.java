package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	
	/**
	 * Numero de documento
	 */
	@JsonProperty(value="numDocumento")
	private int numDocumento;
	
	/**
	 * nombre
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * email
	 */
	@JsonProperty(value="email")
	private String email;
	
	/**
	 * rol
	 */
	@JsonProperty(value="rol")
	private String rol;
	
	/**
	 * usuario
	 */
	@JsonProperty(value="usuario")
	private String usuario;
	
	/**
	 * password
	 */
	@JsonProperty(value="password")
	private String password;

	public Usuario(	@JsonProperty(value="numDocumento")int numDocumento,@JsonProperty(value="nombre")String nombre,@JsonProperty(value="email") String email,	@JsonProperty(value="rol") String rol, 	@JsonProperty(value="usuario")String usuario,
			@JsonProperty(value="password")String password) {
		super();
		this.numDocumento = numDocumento;
		this.nombre = nombre;
		this.email = email;
		this.rol = rol;
		this.usuario = usuario;
		this.password = password;
	}

	public int getNumDocumento() {
		return numDocumento;
	}

	public void setNumDocumento(int numDocumento) {
		this.numDocumento = numDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
