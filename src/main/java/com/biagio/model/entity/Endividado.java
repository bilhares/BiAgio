package com.biagio.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
@Table(name = "endividado")
public class Endividado extends AbstractEntity {

	@NotBlank(message = "Nome obrigatório.")
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotBlank(message = "CPF obrigatório.")
	@Column(name = "cpf", nullable = false)
	private String cpf;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
