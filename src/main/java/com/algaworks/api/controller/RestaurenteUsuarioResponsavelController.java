package com.algaworks.api.controller;

import com.algaworks.api.assembler.UsuarioDTOAssembler;
import com.algaworks.api.model.UsuarioDTO;
import com.algaworks.domain.model.Restaurante;
import com.algaworks.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestaurenteUsuarioResponsavelController {
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @GetMapping
    public List<UsuarioDTO> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(restauranteId);
        return usuarioDTOAssembler.toCollectionDTO(restaurante.getResponsaveis());
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
