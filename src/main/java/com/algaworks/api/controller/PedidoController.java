package com.algaworks.api.controller;

import com.algaworks.api.assembler.PedidoDTOAssembler;
import com.algaworks.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.api.model.PedidoDTO;
import com.algaworks.api.model.PedidoResumoDTO;
import com.algaworks.domain.model.Pedido;
import com.algaworks.domain.repository.PedidoRepository;
import com.algaworks.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoDTOAssembler pedidoDTOAssembler;

    @Autowired
    private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;

    @GetMapping
    public List<PedidoResumoDTO> listar(){
        List<Pedido> pedidos = this.pedidoRepository.findAll();
        return this.pedidoResumoDTOAssembler.toCollectionDTO(pedidos);
    }

    @GetMapping("/{id}")
    public PedidoDTO buscar(@PathVariable Long id){
        Pedido pedido = this.emissaoPedidoService.buscarOuFalhar(id);
        return this.pedidoDTOAssembler.toDTO(pedido);
    }
}
