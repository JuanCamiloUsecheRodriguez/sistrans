package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaBuenCliente {

	@JsonProperty(value="usuarios")
	private List<BuenCliente> usuarios;
	
	public ListaBuenCliente(@JsonProperty(value="usuarios")List<BuenCliente> usuarios){
		this.usuarios = usuarios;
	}

	public List<BuenCliente> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<BuenCliente> usuarios) {
		this.usuarios = usuarios;
	}
}
