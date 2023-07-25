package com.biagio.model.dto;

import java.math.BigDecimal;

public class EmprestimoDTO {

	private Long id;
	private String nome;
	private BigDecimal valor;
	private int qtdParcelas;
	private BigDecimal valorParcela;
	private String descricao;

	public EmprestimoDTO() {
		super();
	}

	public EmprestimoDTO(Long id, String nome, BigDecimal valor, int qtdParcelas, BigDecimal valorParcela,
			String descricao) {
		super();
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.qtdParcelas = qtdParcelas;
		this.valorParcela = valorParcela;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public int getQtdParcelas() {
		return qtdParcelas;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setQtdParcelas(int qtdParcelas) {
		this.qtdParcelas = qtdParcelas;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}

}
