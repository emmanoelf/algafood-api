package com.algaworks.domain.service;

import com.algaworks.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Transactional
    public void confirmar(Long id){
        Pedido pedido = this.emissaoPedidoService.buscarOuFalhar(id);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(Long id){
        Pedido pedido = this.emissaoPedidoService.buscarOuFalhar(id);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(Long id){
        Pedido pedido = this.emissaoPedidoService.buscarOuFalhar(id);
        pedido.entregar();
    }
}
