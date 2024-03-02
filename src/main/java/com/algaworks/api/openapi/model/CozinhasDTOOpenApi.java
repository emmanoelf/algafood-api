package com.algaworks.api.openapi.model;

import com.algaworks.api.model.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;


@ApiModel("Cozinhas model")
@Getter
@Setter
public class CozinhasDTOOpenApi extends PagedModelOpenApi<CozinhaDTO>{

}
