package com.algaworks.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long id){
        this(String.format("Não existe um cadastro de grupo com código %d", id));
    }
}
