package com.biagio.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FaturaDTO {

	private LocalDate dataVencimento;
	private BigDecimal valor;
	private Long cartao;
	private String nomeCartao;

	public FaturaDTO(LocalDate dataVencimento, BigDecimal valor, Long cartaoId, String cartaoNome) {
		this.dataVencimento = dataVencimento;
		this.valor = valor;
		this.cartao = cartaoId;
		this.nomeCartao = cartaoNome;
	}

	public FaturaDTO() {
		super();
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getCartao() {
		return cartao;
	}

	public void setCartao(Long cartao) {
		this.cartao = cartao;
	}

}
