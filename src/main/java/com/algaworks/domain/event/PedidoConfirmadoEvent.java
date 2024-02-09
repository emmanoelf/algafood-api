package com.algaworks.domain.event;

import com.algaworks.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {
    private Pedido pedido;
}
