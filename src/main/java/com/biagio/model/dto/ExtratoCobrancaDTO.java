package com.biagio.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class ExtratoCobrancaDTO {

	private Long endividadoId;
	private String endividadoNome;
	private BigDecimal totalDivida;
	private List<EmprestimoDTO> compras;
	private List<FaturaDTO> faturas;

	public ExtratoCobrancaDTO() {
		super();
	}

	public ExtratoCobrancaDTO(Long endividadoId, String endividadoNome, BigDecimal totalDivida,
			List<EmprestimoDTO> compras, List<FaturaDTO> faturas) {
		super();
		this.endividadoId = endividadoId;
		this.endividadoNome = endividadoNome;
		this.totalDivida = totalDivida;
		this.compras = compras;
		this.faturas = faturas;
	}

	public Long getEndividadoId() {
		return endividadoId;
	}

	public String getEndividadoNome() {
		return endividadoNome;
	}

	public BigDecimal getTotalDivida() {
		return totalDivida;
	}

	public List<EmprestimoDTO> getCompras() {
		return compras;
	}

	public List<FaturaDTO> getFaturas() {
		return faturas;
	}

	public void setEndividadoId(Long endividadoId) {
		this.endividadoId = endividadoId;
	}

	public void setEndividadoNome(String endividadoNome) {
		this.endividadoNome = endividadoNome;
	}

	public void setTotalDivida(BigDecimal totalDivida) {
		this.totalDivida = totalDivida;
	}

	public void setCompras(List<EmprestimoDTO> compras) {
		this.compras = compras;
	}

	public void setFaturas(List<FaturaDTO> faturas) {
		this.faturas = faturas;
	}

}
