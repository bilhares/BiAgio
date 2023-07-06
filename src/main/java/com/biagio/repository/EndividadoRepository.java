package com.biagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biagio.model.entity.Endividado;

public interface EndividadoRepository extends JpaRepository<Endividado, Long> {

	List<Endividado> findByAtivo(boolean ativo);

}
