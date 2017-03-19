package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaUsuarios {
	
	@JsonProperty(value="usuarios")
	private List<Usuario> usuarios;
	
	public ListaUsuarios(@JsonProperty(value="usuarios")List<Usuario> usuarios){
		this.usuarios = usuarios;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
