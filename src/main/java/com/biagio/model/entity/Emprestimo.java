package com.biagio.model.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@NotNull(message = "Valor total obrigatório.")
	@Column(name = "valor_total", nullable = false)
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private BigDecimal valorTotal;

	@NotNull(message = "Valor da parcela obrigatório.")
	@Column(name = "valor_parcela", nullable = false)
	@NumberFormat(style = Style.CURRENCY, pattern = "#,##0.00")
	private BigDecimal valorParcela;

	@NotNull(message = "Quantidade de parcelas obrigatório.")
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

	@OneToMany(mappedBy = "emprestimo")
	private List<ControleEmprestimoParcela> parcelas;

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

	public List<ControleEmprestimoParcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<ControleEmprestimoParcela> parcelas) {
		this.parcelas = parcelas;
	}

}
