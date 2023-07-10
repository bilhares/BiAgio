package com.biagio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biagio.model.dto.FaturaDTO;
import com.biagio.model.entity.StatusParcela;
import com.biagio.repository.EmprestimoRepository;
import com.biagio.repository.FaturaRepository;

@Service
public class FaturaService {

	@Autowired
	EmprestimoRepository emprestimoRepository;

	@Autowired
	FaturaRepository faturaRepository;

	public Page<FaturaDTO> obterFaturas(Pageable pageable) {
		return faturaRepository.obterTodasAsFaturasPorStatus(pageable, StatusParcela.NAO_PAGO);
	}

}
