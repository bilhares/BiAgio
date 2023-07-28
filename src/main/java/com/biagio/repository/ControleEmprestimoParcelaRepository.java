package com.biagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biagio.model.entity.ControleEmprestimoParcela;
import com.biagio.model.entity.Emprestimo;

public interface ControleEmprestimoParcelaRepository extends JpaRepository<ControleEmprestimoParcela, Long> {

	List<ControleEmprestimoParcela> findByEmprestimo(Emprestimo emprestimo);

}
