package com.algaworks.api.controller;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.assembler.UsuarioDTOAssembler;
import com.algaworks.api.model.UsuarioDTO;
import com.algaworks.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    private LinkToResource linkToResource;

    @GetMapping
    public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
        return usuarioDTOAssembler.toCollectionModel(restaurante.getResponsaveis()).removeLinks()
                .add(this.linkToResource.linkToResponsaveisRestaurante(restauranteId));
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        this.cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        this.cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
}
