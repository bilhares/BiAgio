package com.biagio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biagio.model.entity.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{

}
