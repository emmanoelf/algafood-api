package com.algaworks.api.controller;

import com.algaworks.api.LinkToResource;
import com.algaworks.api.assembler.PermissaoDTOAssembler;
import com.algaworks.api.model.PermissaoDTO;
import com.algaworks.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.domain.model.Grupo;
import com.algaworks.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {
    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private PermissaoDTOAssembler permissaoDTOAssembler;

    @Autowired
    private LinkToResource linkToResource;

    @GetMapping
    public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId){
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoDTO> permissoesModel
                = permissaoDTOAssembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(this.linkToResource.linkToGrupoPermissoes(grupoId))
                .add(this.linkToResource.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(this.linkToResource.linkToGrupoPermissaoDesassociacao(
                    grupoId, permissaoModel.getId(), "desassociar"));
        });

        return permissoesModel;
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        this.cadastroGrupoService.associarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        this.cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}
