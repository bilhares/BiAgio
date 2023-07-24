package com.biagio.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat.Style;

import com.biagio.model.security.Usuario;

@SuppressWarnings("serial")
@Entity
@Table(name = "cartao")
public class Cartao extends AbstractEntity {

	@NotBlank(message = "Nome obrigatório.")
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotNull(message = "Número final do cartão obrigatório.")
	@Column(name = "numero_final", nullable = false)
	private int numeroFinal;

	@NotNull(message = "Limite do cartão obrigatório.")
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	@Column(name = "limite", nullable = false)
	private BigDecimal limite;

	@NotNull(message = "Dia de vencimento do cartão obrigatório.")
	@Column(name = "dia_vencimento", nullable = false)
	private int diaVencimento;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumeroFinal() {
		return numeroFinal;
	}

	public void setNumeroFinal(int numeroFinal) {
		this.numeroFinal = numeroFinal;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public int getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(int diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
