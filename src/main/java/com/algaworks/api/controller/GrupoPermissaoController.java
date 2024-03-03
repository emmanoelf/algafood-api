package com.algaworks.api.controller;

import com.algaworks.api.assembler.PermissaoDTOAssembler;
import com.algaworks.api.model.PermissaoDTO;
import com.algaworks.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.domain.model.Grupo;
import com.algaworks.domain.model.Permissao;
import com.algaworks.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {
    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private PermissaoDTOAssembler permissaoDTOAssembler;

    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long grupoId){
        Grupo grupos = this.cadastroGrupoService.buscarOuFalhar(grupoId);
        return permissaoDTOAssembler.toCollectionDTO(grupos.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        this.cadastroGrupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        this.cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
    }
}
