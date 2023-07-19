package com.biagio.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ResumoFaturasHomeDTO {
	private BigDecimal totalDevido;
	private LocalDate dataProxFatura;
	private BigDecimal valorProxFatura;
	private List<DetalheCartaoDTO> cartoes;

	public ResumoFaturasHomeDTO(BigDecimal totalDevido, LocalDate dataProxFatura, BigDecimal valorProxFatura,
			List<DetalheCartaoDTO> cartoes) {
		super();
		this.totalDevido = totalDevido;
		this.dataProxFatura = dataProxFatura;
		this.valorProxFatura = valorProxFatura;
		this.cartoes = cartoes;
	}

	public BigDecimal getTotalDevido() {
		return totalDevido;
	}

	public void setTotalDevido(BigDecimal totalDevido) {
		this.totalDevido = totalDevido;
	}

	public LocalDate getDataProxFatura() {
		return dataProxFatura;
	}

	public void setDataProxFatura(LocalDate dataProxFatura) {
		this.dataProxFatura = dataProxFatura;
	}

	public BigDecimal getValorProxFatura() {
		return valorProxFatura;
	}

	public void setValorProxFatura(BigDecimal valorProxFatura) {
		this.valorProxFatura = valorProxFatura;
	}

	public List<DetalheCartaoDTO> getCartoes() {
		return cartoes;
	}

	public void setCartoes(List<DetalheCartaoDTO> cartoes) {
		this.cartoes = cartoes;
	}

}
