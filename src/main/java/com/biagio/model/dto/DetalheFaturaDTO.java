package com.biagio.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.biagio.model.entity.StatusParcela;

public class DetalheFaturaDTO {

	private Long parcela;
	private Long cartao;
	private Long emprestimo;
	private String nomeCartao;
	private LocalDateTime dataEmprestimo;
	private LocalDate dataVencimento;
	private int numeroParcela;
	private boolean parcelaAtual;
	private StatusParcela status;
	private String nomeCompra;
	private BigDecimal valorParcela;
	private int qtdParcelas;
	private BigDecimal valorTotal;
	private BigDecimal desconto;

	public DetalheFaturaDTO(Long parcela, Long cartao, Long emprestimo, String nomeCartao, LocalDateTime dataEmprestimo,
			LocalDate dataVencimento, int numeroParcela, boolean parcelaAtual, StatusParcela status, String nomeCompra,
			BigDecimal valorParcela, int qtdParcelas, BigDecimal valorTotal, BigDecimal desconto) {
		super();
		this.parcela = parcela;
		this.cartao = cartao;
		this.emprestimo = emprestimo;
		this.nomeCartao = nomeCartao;
		this.dataEmprestimo = dataEmprestimo;
		this.dataVencimento = dataVencimento;
		this.numeroParcela = numeroParcela;
		this.parcelaAtual = parcelaAtual;
		this.status = status;
		this.nomeCompra = nomeCompra;
		this.valorParcela = valorParcela;
		this.qtdParcelas = qtdParcelas;
		this.valorTotal = valorTotal;
		this.desconto = desconto == null ? new BigDecimal(0) : desconto;
	}

	public DetalheFaturaDTO() {
		super();
	}

	public Long getParcela() {
		return parcela;
	}

	public void setParcela(Long parcela) {
		this.parcela = parcela;
	}

	public Long getCartao() {
		return cartao;
	}

	public void setCartao(Long cartao) {
		this.cartao = cartao;
	}

	public Long getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Long emprestimo) {
		this.emprestimo = emprestimo;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public LocalDateTime getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public int getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(int numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public boolean isParcelaAtual() {
		return parcelaAtual;
	}

	public void setParcelaAtual(boolean parcelaAtual) {
		this.parcelaAtual = parcelaAtual;
	}

	public StatusParcela getStatus() {
		return status;
	}

	public void setStatus(StatusParcela status) {
		this.status = status;
	}

	public String getNomeCompra() {
		return nomeCompra;
	}

	public void setNomeCompra(String nomeCompra) {
		this.nomeCompra = nomeCompra;
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

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

}
