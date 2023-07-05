package com.biagio.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "emprestimo")
public class Emprestimo extends AbstractEntity {

	@NotBlank(message = "Nome obrigatório.")
	@Column(name = "nome", nullable = false)
	private String nome;

	@NotBlank(message = "Valor total obrigatório.")
	@Column(name = "valor_total", nullable = false)
	private BigDecimal valorTotal;

	@NotBlank(message = "Valor da parcela obrigatório.")
	@Column(name = "valor_parcela", nullable = false)
	private BigDecimal valorParcela;

	@NotBlank(message = "Quantidade de parcelas obrigatório.")
	@Column(name = "qtd_parcelas", nullable = false)
	private int qtdParcelas;

	@NotBlank(message = "Descrição obrigatório.")
	@Column(name = "descricao", nullable = false)
	private String descricao;

	@NotNull(message = "Cartao obrigatório.")
	@ManyToOne
	@JoinColumn(name = "cartao_id")
	private Cartao cartao;

	@NotNull(message = "Endividado obrigatório.")
	@ManyToOne
	@JoinColumn(name = "endividado_id")
	private Endividado endividado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

	public int getQtdParcelas() {
		return qtdParcelas;
	}

	public void setQtdParcelas(int qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public Endividado getEndividado() {
		return endividado;
	}

	public void setEndividado(Endividado endividado) {
		this.endividado = endividado;
	}

}
