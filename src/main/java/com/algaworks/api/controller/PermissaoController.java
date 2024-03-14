package com.algaworks.api.controller;

import com.algaworks.api.assembler.PermissaoDTOAssembler;
import com.algaworks.api.model.PermissaoDTO;
import com.algaworks.api.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.domain.model.Permissao;
import com.algaworks.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoDTOAssembler permissaoDTOAssembler;

    @Override
    @GetMapping
    public CollectionModel<PermissaoDTO> listar() {
        List<Permissao> permissoesDtos = this.permissaoRepository.findAll();

        return permissaoDTOAssembler.toCollectionModel(permissoesDtos);
    }
}
