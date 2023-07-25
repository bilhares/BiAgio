package com.biagio.model.dto;

import java.math.BigDecimal;

public class ResumoEndividadosDTO {

	private Long id;
	private String nome;
	private BigDecimal totalDivida;

	public ResumoEndividadosDTO() {
		super();
	}

	public ResumoEndividadosDTO(Long id, String nome, BigDecimal totalDivida) {
		super();
		this.id = id;
		this.nome = nome;
		this.totalDivida = totalDivida;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public BigDecimal getTotalDivida() {
		return totalDivida;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTotalDivida(BigDecimal totalDivida) {
		this.totalDivida = totalDivida;
	}

}
