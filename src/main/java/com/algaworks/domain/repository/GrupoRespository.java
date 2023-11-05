package com.algaworks.domain.repository;

import com.algaworks.domain.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRespository extends JpaRepository<Grupo, Long> {
}
