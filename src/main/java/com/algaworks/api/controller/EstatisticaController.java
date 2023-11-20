package com.algaworks.api.controller;

import com.algaworks.domain.dto.VendaDiaria;
import com.algaworks.domain.filter.VendaDiariaFilter;
import com.algaworks.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticaController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro){
        return vendaQueryService.consultarVendasDiarias(filtro);
    }
}
