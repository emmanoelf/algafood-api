package com.algaworks.api.controller;

import com.algaworks.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.api.model.FormaPagamentoDTO;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/restaurantes/{id}/formas-pagamento")
public class RestauranteFormaPagamentoController {
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar(@PathVariable Long id){
        Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(id);
        return this.formaPagamentoDTOAssembler.toColletionDTO(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long id, @PathVariable Long idFormaPagamento){
        this.cadastroRestauranteService.desassociarFormaPagamento(id, idFormaPagamento);
    }

    @PutMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long id, @PathVariable Long idFormaPagamento){
        this.cadastroRestauranteService.associarFormaPagamento(id, idFormaPagamento);
    }
}
