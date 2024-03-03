package com.algaworks.api.openapi.model;

import com.algaworks.api.model.PedidoResumoDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Pedidos model")
@Getter
@Setter
public class PedidosResumoDTOOpenApi extends PagedModelOpenApi<PedidoResumoDTO> {
}
