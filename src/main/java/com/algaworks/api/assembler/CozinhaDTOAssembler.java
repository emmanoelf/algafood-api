package com.algaworks.api.assembler;

import com.algaworks.api.model.CozinhaDTO;
import com.algaworks.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDTO toDTO(Cozinha cozinha){
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toColletionDTO(List<Cozinha> cozinhas){
        return cozinhas.stream()
                .map(cozinha -> toDTO(cozinha)).collect(Collectors.toList());
    }
}
