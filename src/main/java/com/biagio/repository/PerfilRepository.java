package com.biagio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biagio.model.security.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
