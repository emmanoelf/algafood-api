package com.algaworks.domain.service;

import com.algaworks.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.domain.model.Produto;
import com.algaworks.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto save(Produto produto){
        return this.produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId){
        return this.produtoRepository.findById(restauranteId, produtoId).orElseThrow(() ->
                new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }

}
