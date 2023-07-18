package com.biagio.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "controle_emprestimo_parcela")
public class ControleEmprestimoParcela extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "emprestimo_id")
	private Emprestimo emprestimo;

	@Column(name = "data_emprestimo", nullable = false)
	private LocalDateTime dataEmprestimo;

	@Column(name = "data_vencimento", nullable = false)
	private LocalDate dataVencimento;

	@Column(name = "numero_parcela", nullable = false)
	private int numeroParcela;

	@Column(name = "parcela_atual", nullable = false)
	private boolean parcelaAtual;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusParcela status;

	@Column(name = "desconto", nullable = false)
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private BigDecimal desconto;

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	public LocalDateTime getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
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

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

}
