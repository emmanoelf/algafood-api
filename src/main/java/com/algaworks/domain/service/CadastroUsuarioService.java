package com.algaworks.domain.service;

import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.domain.model.Grupo;
import com.algaworks.domain.model.Usuario;
import com.algaworks.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Transactional
    public Usuario salvar(Usuario usuario){
        usuarioRepository.detach(usuario);
        Optional<Usuario> usuarioExistente = this.usuarioRepository.findByEmail(usuario.getEmail());

        if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)){
            throw new NegocioException(
                    String.format("Já exite um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        return this.usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha){
        Usuario usuario = buscarOuFalhar(id);

        if(!usuario.passwordMatch(senhaAtual)){
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    public Usuario buscarOuFalhar(Long id){
        return this.usuarioRepository.findById(id).orElseThrow(() ->
                new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = this.buscarOuFalhar(usuarioId);
        Grupo grupo = this.cadastroGrupoService.buscarOuFalhar(grupoId);
        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId){
        Usuario usuario = this.buscarOuFalhar(usuarioId);
        Grupo grupo = this.cadastroGrupoService.buscarOuFalhar(grupoId);
        usuario.desassociarGrupo(grupo);
    }
}
