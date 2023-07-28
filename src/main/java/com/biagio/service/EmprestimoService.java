package com.biagio.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biagio.model.entity.ControleEmprestimoParcela;
import com.biagio.model.entity.Emprestimo;
import com.biagio.model.entity.StatusParcela;
import com.biagio.repository.ControleEmprestimoParcelaRepository;
import com.biagio.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

	@Autowired
	EmprestimoRepository emprestimoRepository;

	@Autowired
	ControleEmprestimoParcelaRepository controleParcelaRepository;

	@Transactional(readOnly = false)
	public void criarEmprestimo(Emprestimo emprestimo) {

		LocalDate dataVencimento = LocalDate.now().plusMonths(1)
				.withDayOfMonth(emprestimo.getCartao().getDiaVencimento());

		emprestimo.setAtivo(true);
		emprestimoRepository.save(emprestimo);

		for (int i = 1; i <= emprestimo.getQtdParcelas(); i++) {

			ControleEmprestimoParcela parcela = new ControleEmprestimoParcela();
			parcela.setAtivo(true);
			parcela.setDataVencimento(dataVencimento);
			parcela.setDataEmprestimo(LocalDateTime.now());
			parcela.setEmprestimo(emprestimo);
			parcela.setNumeroParcela(i);
			if (i == 1)
				parcela.setParcelaAtual(true);

			parcela.setStatus(StatusParcela.NAO_PAGO);

			controleParcelaRepository.save(parcela);

			dataVencimento = dataVencimento.plusMonths(1);
		}
	}

	@Transactional(readOnly = false)
	public void excluirEmprestimo(Long id) {
		Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
		List<ControleEmprestimoParcela> parcelas = controleParcelaRepository.findByEmprestimo(emprestimo.get());

		controleParcelaRepository.deleteAll(parcelas);
		emprestimoRepository.delete(emprestimo.get());
	}
}
