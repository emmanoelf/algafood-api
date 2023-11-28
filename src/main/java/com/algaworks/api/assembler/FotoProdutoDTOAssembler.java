package com.algaworks.api.assembler;

import com.algaworks.api.model.FotoProdutoDTO;
import com.algaworks.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toDTO(FotoProduto fotoProduto){
        return this.modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }
}
