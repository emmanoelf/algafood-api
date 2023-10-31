package com.algaworks.api.assembler;

import com.algaworks.api.model.CozinhaDTO;
import com.algaworks.api.model.RestauranteDTO;
import com.algaworks.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {
    public RestauranteDTO toDTO(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);

        return restauranteDTO;
    }

    public List<RestauranteDTO> toColletionDTO(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> toDTO(restaurante))
                .collect(Collectors.toList());
    }
}
