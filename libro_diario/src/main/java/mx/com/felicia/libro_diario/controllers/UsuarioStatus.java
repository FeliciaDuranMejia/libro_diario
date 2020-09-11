package mx.com.felicia.libro_diario.controllers;

import mx.com.felicia.libro_diario.dal.models.Usuarios;

public class UsuarioStatus {
	private Usuarios usuario;
	private boolean status;
	
	public UsuarioStatus(Usuarios usuario, boolean status) {
		super();
		this.usuario = usuario;
		this.status = status;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
}