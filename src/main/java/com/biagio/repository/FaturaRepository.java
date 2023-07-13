package com.biagio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.biagio.model.dto.DetalheFaturaDTO;
import com.biagio.model.dto.FaturaDTO;
import com.biagio.model.entity.StatusParcela;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class FaturaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<FaturaDTO> obterTodasAsFaturasPorStatus(Pageable pageable, StatusParcela status) {

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT NEW com.biagio.model.dto.FaturaDTO(ce.dataVencimento, SUM(e.valorParcela), c.id, c.nome) ");
		sql.append("FROM ControleEmprestimoParcela ce ");
		sql.append("JOIN ce.emprestimo e ");
		sql.append("JOIN e.cartao c ");
		sql.append("WHERE e.ativo = 1 ");
		sql.append("AND ce.status = :statusParcela ");
		sql.append("GROUP BY ce.dataVencimento, c.id, c.nome ");
		sql.append("ORDER BY ce.dataVencimento");

		Long totalResults = count(status, sql);

		List<FaturaDTO> resultList = entityManager.createQuery(sql.toString(), FaturaDTO.class)
				.setParameter("statusParcela", status).setFirstResult(startItem).setMaxResults(pageSize)
				.getResultList();

		Page<FaturaDTO> page = new PageImpl<>(resultList, PageRequest.of(currentPage, pageSize), totalResults);

		return page;
	}

	private Long count(StatusParcela status, StringBuilder sql) {

		int totalCount = entityManager.createQuery(sql.toString(), FaturaDTO.class)
				.setParameter("statusParcela", status).getResultList().size();

		return Long.valueOf(totalCount);
	}

	public List<DetalheFaturaDTO> obterDetalhesDaFatura(Long cartaoId, LocalDate dataVencimento) {
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT NEW com.biagio.model.dto.DetalheFaturaDTO( ");
		sql.append("ce.id, c.id, e.id, ");
		sql.append("c.nome, ce.dataEmprestimo, ce.dataVencimento, ");
		sql.append("ce.numeroParcela, ce.parcelaAtual, ce.status, ");
		sql.append("e.nome, e.valorParcela, e.qtdParcelas, e.valorTotal ");
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
}
