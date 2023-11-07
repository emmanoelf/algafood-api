package com.algaworks.api.assembler;

import com.algaworks.api.model.input.ProdutoInput;
import com.algaworks.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInput produtoInput){
        return this.modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto){
        this.modelMapper.map(produtoInput, produto);
    }
}
