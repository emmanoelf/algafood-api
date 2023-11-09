package com.algaworks.domain.service;

import com.algaworks.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.domain.model.Pedido;
import com.algaworks.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long id){
        return this.pedidoRepository.findById(id).orElseThrow(() ->
                new PedidoNaoEncontradoException(id));
    }
}
