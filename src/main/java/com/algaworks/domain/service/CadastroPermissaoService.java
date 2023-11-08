package com.algaworks.domain.service;

import com.algaworks.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.domain.model.Permissao;
import com.algaworks.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {
    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long id){
        return this.permissaoRepository.findById(id).orElseThrow(() ->
                new PermissaoNaoEncontradaException(id));
    }
}
