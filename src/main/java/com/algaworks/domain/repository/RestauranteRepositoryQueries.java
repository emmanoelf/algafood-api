package com.algaworks.domain.repository;

import com.algaworks.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
    List<Restaurante> findComFreteGratis(String nome);
}
