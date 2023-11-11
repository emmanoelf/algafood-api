package com.algaworks.domain.service;

import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.model.Pedido;
import com.algaworks.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(Long id){
        Pedido pedido = this.emissaoPedidoService.buscarOuFalhar(id);
        if(!pedido.getStatus().equals(StatusPedido.CRIADO)){
            throw new NegocioException(String.format("Status do pedido %d não pode ser alterado de %s para %s",
                    pedido.getId(),
                    pedido.getStatus().getDescricao(),
                    StatusPedido.CONFIRMADO.getDescricao())
            );
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }
}
