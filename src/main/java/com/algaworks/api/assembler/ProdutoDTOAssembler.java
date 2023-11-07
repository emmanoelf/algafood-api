package com.algaworks.api.assembler;

import com.algaworks.api.model.ProdutoDTO;
import com.algaworks.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ProdutoDTO toDTO(Produto produto){
        return this.modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos){
        return produtos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
