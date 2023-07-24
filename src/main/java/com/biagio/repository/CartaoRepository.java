package com.biagio.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biagio.model.entity.Cartao;
import com.biagio.model.security.Usuario;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

	@Query("select c from Cartao c where c.nome like %:search%")
	Page<Cartao> findByNome(@Param("search") String search, Pageable pageable);

	List<Cartao> findByAtivoAndUsuario(boolean ativo, Usuario usuario);

	List<Cartao> findByUsuario(Usuario usuario);
}
