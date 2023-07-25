package com.biagio.model.dto.amqp;

import com.biagio.model.security.Usuario;

public class ConsumerDTO {
	Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
