package com.biagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biagio.model.entity.Endividado;

public interface EndividadoRepository extends JpaRepository<Endividado, Long> {

	List<Endividado> findByAtivo(boolean ativo);

	@Query("select en.id from Endividado en where en.ativo = true ")
	List<Long> findAllIds();

}
