package com.algaworks.domain.service;

import com.algaworks.domain.dto.VendaDiaria;
import com.algaworks.domain.filter.VendaDiariaFilter;

import java.util.List;

public interface VendaQueryService {
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
