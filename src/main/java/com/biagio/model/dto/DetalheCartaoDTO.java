package com.biagio.model.dto;

import java.math.BigDecimal;

public class DetalheCartaoDTO {
	private Long id;
	private String nomeCartao;
	private BigDecimal limite;
	private BigDecimal limiteDisponivel;
	private BigDecimal totaGasto;

	public DetalheCartaoDTO() {
		super();
	}

	public DetalheCartaoDTO(Long id, String nomeCartao, BigDecimal limite, BigDecimal limiteDisponivel, BigDecimal totalGasto) {
		super();
		this.id = id;
		this.nomeCartao = nomeCartao;
		this.limite = limite;
		this.limiteDisponivel = limiteDisponivel;
		this.totaGasto = totalGasto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	public BigDecimal getLimiteDisponivel() {
		return limiteDisponivel;
	}

	public void setLimiteDisponivel(BigDecimal limiteDisponivel) {
		this.limiteDisponivel = limiteDisponivel;
	}

	public BigDecimal getTotaGasto() {
		return totaGasto;
	}

	public void setTotaGasto(BigDecimal totaGasto) {
		this.totaGasto = totaGasto;
	}

}
