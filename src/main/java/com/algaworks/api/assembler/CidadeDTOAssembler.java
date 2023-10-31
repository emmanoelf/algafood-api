package com.algaworks.api.assembler;

import com.algaworks.api.model.CidadeDTO;
import com.algaworks.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CidadeDTO toDTO(Cidade cidade){
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades){
        return cidades.stream()
                .map(this::toDTO).collect(Collectors.toList());
    }
}
