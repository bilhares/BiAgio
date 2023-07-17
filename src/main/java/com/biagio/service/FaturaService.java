package com.biagio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biagio.model.dto.DetalheFaturaDTO;
import com.biagio.model.dto.FaturaDTO;
import com.biagio.model.entity.ControleEmprestimoParcela;
import com.biagio.model.entity.StatusParcela;
import com.biagio.repository.ControleEmprestimoParcelaRepository;
import com.biagio.repository.FaturaRepository;

@Service
public class FaturaService {

	@Autowired
	ControleEmprestimoParcelaRepository parcelaRepository;

	@Autowired
	FaturaRepository faturaRepository;

	public Page<FaturaDTO> obterFaturas(Pageable pageable) {
		return faturaRepository.obterTodasAsFaturasPorStatus(pageable, StatusParcela.NAO_PAGO);
	}

	public List<DetalheFaturaDTO> obterDetalhesFatura(Long cartaoId, LocalDate dataVencimento) {
		return faturaRepository.obterDetalhesDaFatura(cartaoId, dataVencimento);
	}

	public void efetuarPagamentoTotal(Long cartaoId, LocalDate dtVencimento) {
		List<DetalheFaturaDTO> faturas = obterDetalhesFatura(cartaoId, dtVencimento);

		faturas.forEach(fatura -> {
			efetuarPagamentoParcela(fatura.getParcela());
		});
	}

	public ControleEmprestimoParcela efetuarPagamentoParcela(Long idParcela) {
		Optional<ControleEmprestimoParcela> parcelaOpt = parcelaRepository.findById(idParcela);
		if (parcelaOpt.isPresent()) {
			ControleEmprestimoParcela parcela = parcelaOpt.get();
			parcela.setStatus(StatusParcela.PAGO);
			parcelaRepository.save(parcela);

			return parcela;
		}

		return null;
	}

}
