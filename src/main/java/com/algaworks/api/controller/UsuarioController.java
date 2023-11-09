package com.algaworks.api.controller;

import com.algaworks.api.assembler.UsuarioDTOAssembler;
import com.algaworks.api.assembler.UsuarioInputDisassembler;
import com.algaworks.api.model.UsuarioDTO;
import com.algaworks.api.model.input.UsuarioInput;
import com.algaworks.api.model.input.UsuarioNovaSenhaInput;
import com.algaworks.api.model.input.UsuarioNovoCadastroInput;
import com.algaworks.domain.model.Usuario;
import com.algaworks.domain.repository.UsuarioRepository;
import com.algaworks.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioDTO> listar(){
        List<Usuario> usuarios = this.usuarioRepository.findAll();
        return this.usuarioDTOAssembler.toCollectionDTO(usuarios);
    }

    @GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable Long id){
        Usuario usuario = this.cadastroUsuarioService.buscarOuFalhar(id);
        return this.usuarioDTOAssembler.toDTO(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioNovoCadastroInput usuarioNovoCadastroInput){
        Usuario usuario = this.usuarioInputDisassembler.toDomainObject(usuarioNovoCadastroInput);
        usuario = this.cadastroUsuarioService.salvar(usuario);
        return this.usuarioDTOAssembler.toDTO(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput){
        Usuario usuario = this.cadastroUsuarioService.buscarOuFalhar(id);
        this.usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuario);
        usuario = this.cadastroUsuarioService.salvar(usuario);
        return this.usuarioDTOAssembler.toDTO(usuario);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid UsuarioNovaSenhaInput usuarioNovaSenhaInput){
        this.cadastroUsuarioService.alterarSenha(id, usuarioNovaSenhaInput.getSenhaAtual(), usuarioNovaSenhaInput.getNovaSenha());
    }

}
