package com.algaworks.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException{
    public PermissaoNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long id){
        this(String.format("Não existe um cadastro de permissão com código %d", id));
    }
}
