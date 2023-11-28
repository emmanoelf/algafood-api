package com.algaworks.domain.service;

import com.algaworks.domain.model.FotoProduto;
import com.algaworks.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto){
        Long restauranteId = foto.getRestauranteId();
        Long produtoId = foto.getProduto().getId();

        Optional<FotoProduto> fotoExistente = this.produtoRepository.findFotoById(restauranteId, produtoId);

        if(fotoExistente.isPresent()){
            this.produtoRepository.delete(fotoExistente.get());
        }

        return produtoRepository.save(foto);
    }
}
