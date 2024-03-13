package com.algaworks.api.controller;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.api.model.FormaPagamentoDTO;
import com.algaworks.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurantes/{id}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @Autowired
    private LinkToResource linkToResource;

    @GetMapping
    public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long id){
        Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(id);
        CollectionModel<FormaPagamentoDTO> formasPagamentoDto = formaPagamentoDTOAssembler
                .toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(this.linkToResource.linkToRestauranteFormasPagamento(id))
                .add(this.linkToResource.linkToFormaPagamentAssociacao(id, "associar"));

        formasPagamentoDto.getContent().forEach(formaPagamento -> {
            formaPagamento.add(this.linkToResource
                    .linkToFormaPagamentoDesassociacao(id, formaPagamento.getId(), "desassociar"));
        });

        return formasPagamentoDto;
    }

    @DeleteMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long id, @PathVariable Long idFormaPagamento){
        this.cadastroRestauranteService.desassociarFormaPagamento(id, idFormaPagamento);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idFormaPagamento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long id, @PathVariable Long idFormaPagamento){
        this.cadastroRestauranteService.associarFormaPagamento(id, idFormaPagamento);

        return ResponseEntity.noContent().build();
    }
}
