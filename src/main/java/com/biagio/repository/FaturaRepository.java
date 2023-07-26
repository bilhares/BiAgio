package com.biagio.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.biagio.model.dto.DetalheCartaoDTO;
import com.biagio.model.dto.DetalheFaturaDTO;
import com.biagio.model.dto.EmprestimoDTO;
import com.biagio.model.dto.FaturaDTO;
import com.biagio.model.dto.ResumoEndividadosDTO;
import com.biagio.model.entity.Endividado;
import com.biagio.model.entity.StatusParcela;
import com.biagio.service.UsuarioService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class FaturaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	UsuarioService usuarioService;

	public Page<FaturaDTO> obterTodasAsFaturasPorStatusEEndividados(Pageable pageable, List<StatusParcela> status,
			List<Long> endividados) {

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT NEW com.biagio.model.dto.FaturaDTO(ce.dataVencimento, (SUM(e.valorParcela) - SUM(COALESCE(ce.desconto, 0))), c.id, c.nome) ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("JOIN e.endividado en ");
		sql.append("WHERE e.ativo = 1 ");
		sql.append("AND ce.status in :statusList ");
		sql.append("AND c.usuario = :usuario ");
		sql.append("AND en.id in :endividados ");
		sql.append("GROUP BY ce.dataVencimento, c.id, c.nome, ce.desconto ");
		sql.append("ORDER BY ce.dataVencimento");

		Long totalResults = count(status, endividados, sql);

		List<FaturaDTO> resultList = entityManager.createQuery(sql.toString(), FaturaDTO.class)
				.setParameter("statusList", status).setParameter("usuario", usuarioService.obterUsuarioLogado())
				.setParameter("endividados", endividados).setFirstResult(startItem).setMaxResults(pageSize)
				.getResultList();

		Page<FaturaDTO> page = new PageImpl<>(resultList, PageRequest.of(currentPage, pageSize), totalResults);

		return page;
	}

	private Long count(List<StatusParcela> status, List<Long> endividados, StringBuilder sql) {

		int totalCount = entityManager.createQuery(sql.toString(), FaturaDTO.class).setParameter("statusList", status)
				.setParameter("usuario", usuarioService.obterUsuarioLogado()).setParameter("endividados", endividados)
				.getResultList().size();

		return Long.valueOf(totalCount);
	}

	public List<DetalheFaturaDTO> obterDetalhesDaFatura(Long cartaoId, LocalDate dataVencimento) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT NEW com.biagio.model.dto.DetalheFaturaDTO( ");
		sql.append("ce.id, c.id, e.id, ");
		sql.append("c.nome, ce.dataEmprestimo, ce.dataVencimento, ");
		sql.append("ce.numeroParcela, ce.parcelaAtual, ce.status, ");
		sql.append("e.nome, e.valorParcela, e.qtdParcelas, e.valorTotal, ce.desconto ");
		sql.append(") ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("WHERE ");
		sql.append("ce.dataVencimento = :dataVencimento ");
		sql.append("AND c.id = :cartaoId ");

		List<DetalheFaturaDTO> resultList = entityManager.createQuery(sql.toString(), DetalheFaturaDTO.class)
				.setParameter("dataVencimento", dataVencimento).setParameter("cartaoId", cartaoId).getResultList();

		return resultList;
	}

	public BigDecimal obterValorTotalDosEmprestimos() {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT sum(DISTINCT COALESCE(e.valorTotal,0)) ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("WHERE e.ativo = 1 ");
		sql.append("AND ce.status in :statusList ");
		sql.append("AND c.usuario = :usuario ");

		List<StatusParcela> statusList = List.of(StatusParcela.NAO_PAGO, StatusParcela.ATRASADO);

		Query query = entityManager.createQuery(sql.toString()).setParameter("statusList", statusList)
				.setParameter("usuario", usuarioService.obterUsuarioLogado());

		BigDecimal sum = (BigDecimal) query.getSingleResult();

		return sum != null ? sum : BigDecimal.ZERO;
	}

	public List<DetalheCartaoDTO> obterDetalhesCartoes() {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT NEW com.biagio.model.dto.DetalheCartaoDTO( ");
		sql.append(
				" c.id, c.nome, c.limite, (c.limite - sum(distinct e.valorTotal)), sum(distinct e.valorTotal) totalGasto ");
		sql.append(") ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("WHERE ");
		sql.append("e.ativo = 1 ");
		sql.append("AND ce.status in :statusList ");
		sql.append("AND c.usuario = :usuario ");
		sql.append("GROUP BY c.id, c.nome, c.limite ");

		List<StatusParcela> statusList = List.of(StatusParcela.NAO_PAGO, StatusParcela.ATRASADO);
		List<DetalheCartaoDTO> resultList = entityManager.createQuery(sql.toString(), DetalheCartaoDTO.class)
				.setParameter("statusList", statusList).setParameter("usuario", usuarioService.obterUsuarioLogado())
				.getResultList();

		return resultList;
	}

	public List<ResumoEndividadosDTO> obterResumoDosEndividados() {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT NEW com.biagio.model.dto.ResumoEndividadosDTO( ");
		sql.append(" en.id, en.nome,  (SUM(e.valorParcela) - SUM(COALESCE(ce.desconto, 0)))");
		sql.append(") ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("JOIN e.endividado en ");
		sql.append("WHERE ");
		sql.append("e.ativo = 1 ");
		sql.append("AND ce.status in :statusList ");
		sql.append("AND c.usuario = :usuario ");
		sql.append("GROUP BY en.id, en.nome ");

		List<StatusParcela> statusList = List.of(StatusParcela.NAO_PAGO, StatusParcela.ATRASADO);

		return entityManager.createQuery(sql.toString(), ResumoEndividadosDTO.class)
				.setParameter("statusList", statusList).setParameter("usuario", usuarioService.obterUsuarioLogado())
				.getResultList();
	}

	public List<EmprestimoDTO> obterEmprestimosDeUmEndividado(Long endividadoId) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT NEW com.biagio.model.dto.EmprestimoDTO( ");
		sql.append("  e.id, e.nome, e.valorTotal, e.qtdParcelas, e.valorParcela, e.descricao ");
		sql.append(") ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("JOIN e.endividado en ");
		sql.append("WHERE ");
		sql.append("e.ativo = 1 ");
		sql.append("AND ce.status in :statusList ");
		sql.append("AND c.usuario = :usuario ");
		sql.append("AND en.id = :endividado ");
		sql.append(" ORDER BY e.id ");

		List<StatusParcela> statusList = List.of(StatusParcela.NAO_PAGO, StatusParcela.ATRASADO);

		return entityManager.createQuery(sql.toString(), EmprestimoDTO.class).setParameter("statusList", statusList)
				.setParameter("usuario", usuarioService.obterUsuarioLogado()).setParameter("endividado", endividadoId)
				.getResultList();
	}

	public List<FaturaDTO> obterFaturasDeUmEndividado(Long endividadoId) {
		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT NEW com.biagio.model.dto.FaturaDTO(ce.dataVencimento, (SUM(e.valorParcela) - SUM(COALESCE(ce.desconto, 0))), c.id, c.nome) ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("JOIN e.endividado en ");
		sql.append("WHERE e.ativo = 1 ");
		sql.append("AND ce.status in :statusList ");
		sql.append("AND c.usuario = :usuario ");
		sql.append("AND en.id = :endividado ");
		sql.append("GROUP BY ce.dataVencimento, c.id, c.nome, ce.desconto ");
		sql.append("ORDER BY ce.dataVencimento");

		List<StatusParcela> statusList = List.of(StatusParcela.NAO_PAGO, StatusParcela.ATRASADO);

		return entityManager.createQuery(sql.toString(), FaturaDTO.class).setParameter("statusList", statusList)
				.setParameter("usuario", usuarioService.obterUsuarioLogado()).setParameter("endividado", endividadoId)
				.getResultList();
	}
}
