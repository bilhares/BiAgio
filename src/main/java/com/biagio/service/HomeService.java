package com.biagio.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.biagio.model.dto.DetalheCartaoDTO;
import com.biagio.model.dto.FaturaDTO;
import com.biagio.model.dto.ResumoFaturasHomeDTO;
import com.biagio.model.entity.StatusParcela;
import com.biagio.repository.EndividadoRepository;
import com.biagio.repository.FaturaRepository;

@Service
public class HomeService {

	@Autowired
	FaturaRepository faturaRepository;
	
	@Autowired
	EndividadoRepository endividadosRepository;

	public ResumoFaturasHomeDTO obterResumoDasFaturas() {

		BigDecimal totalDevido = faturaRepository.obterValorTotalDosEmprestimos();
		
		Page<FaturaDTO> faturas = faturaRepository.obterTodasAsFaturasPorStatusEEndividados(PageRequest.of(0, 1),
				List.of(StatusParcela.NAO_PAGO, StatusParcela.ATRASADO), endividadosRepository.findAllIds());

		FaturaDTO proximaFatura = faturas.isEmpty() ? new FaturaDTO() : faturas.getContent().get(0);

		List<DetalheCartaoDTO> detalhesCartoes = faturaRepository.obterDetalhesCartoes();

		return new ResumoFaturasHomeDTO(totalDevido, proximaFatura.getDataVencimento(), proximaFatura.getValor(),
				detalhesCartoes);
	}

}
