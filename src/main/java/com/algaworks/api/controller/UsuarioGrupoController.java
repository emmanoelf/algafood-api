package com.algaworks.api.controller;

import com.algaworks.api.assembler.GrupoDTOAssembler;
import com.algaworks.api.model.GrupoDTO;
import com.algaworks.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.domain.model.Usuario;
import com.algaworks.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @GetMapping
    public List<GrupoDTO> listar(@PathVariable Long usuarioId){
        Usuario usuario = this.cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return this.grupoDTOAssembler.toCollectionList(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        this.cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        this.cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
    }
}
