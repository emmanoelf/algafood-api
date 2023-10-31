package com.algaworks.api.assembler;

import com.algaworks.api.model.RestauranteDTO;
import com.algaworks.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO toDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> toColletionDTO(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> toDTO(restaurante))
                .collect(Collectors.toList());
    }
}
