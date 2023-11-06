package com.algaworks.domain.service;

import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.domain.model.Usuario;
import com.algaworks.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario){
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
}
