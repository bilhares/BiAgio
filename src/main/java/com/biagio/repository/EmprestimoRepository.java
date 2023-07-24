package com.biagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biagio.model.entity.Cartao;
import com.biagio.model.entity.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

	List<Emprestimo> findByCartaoIn(List<Cartao> cartoes);

}
