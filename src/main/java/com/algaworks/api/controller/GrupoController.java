package com.algaworks.api.controller;

import com.algaworks.api.assembler.GrupoDTOAssembler;
import com.algaworks.api.assembler.GrupoInputDisassembler;
import com.algaworks.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.api.model.GrupoDTO;
import com.algaworks.api.model.input.GrupoInput;
import com.algaworks.domain.model.Grupo;
import com.algaworks.domain.repository.GrupoRespository;
import com.algaworks.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
    @Autowired
    private GrupoRespository grupoRespository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoDTO> listar(){
        List<Grupo> grupos = this.grupoRespository.findAll();
        return this.grupoDTOAssembler.toCollectionList(grupos);
    }

    @GetMapping("/{id}")
    public GrupoDTO buscar(@PathVariable Long id){
        Grupo grupo = this.cadastroGrupoService.buscarOuFalhar(id);
        return this.grupoDTOAssembler.toDTO(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput){
        Grupo grupo = this.grupoInputDisassembler.toDomainObject(grupoInput);
        grupo = this.cadastroGrupoService.salvar(grupo);
        return this.grupoDTOAssembler.toDTO(grupo);
    }

    @PutMapping("/{id}")
    public GrupoDTO alterar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput){
        Grupo grupo = this.cadastroGrupoService.buscarOuFalhar(id);
        this.grupoInputDisassembler.copyToDomainObject(grupoInput, grupo);
        grupo = this.cadastroGrupoService.salvar(grupo);
        return this.grupoDTOAssembler.toDTO(grupo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        this.cadastroGrupoService.excluir(id);
    }
}
