package com.algaworks.api.controller;

import com.algaworks.api.assembler.PedidoDTOAssembler;
import com.algaworks.api.assembler.PedidoInputDisassembler;
import com.algaworks.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.api.model.PedidoDTO;
import com.algaworks.api.model.PedidoResumoDTO;
import com.algaworks.api.model.input.PedidoInput;
import com.algaworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.model.Pedido;
import com.algaworks.domain.model.Usuario;
import com.algaworks.domain.repository.PedidoRepository;
import com.algaworks.domain.repository.filter.PedidoFilter;
import com.algaworks.domain.service.EmissaoPedidoService;
import com.algaworks.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public List<PedidoResumoDTO> pesquisar(PedidoFilter filtro){
        List<Pedido> pedidos = this.pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
        return this.pedidoResumoDTOAssembler.toCollectionDTO(pedidos);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDTO buscar(@PathVariable String codigoPedido){
        Pedido pedido = this.emissaoPedidoService.buscarOuFalhar(codigoPedido);
        return this.pedidoDTOAssembler.toDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO adicionar(@RequestBody @Valid PedidoInput pedidoInput){
        try{
            Pedido pedido = this.pedidoInputDisassembler.toDomainObject(pedidoInput);
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            pedido = this.emissaoPedidoService.emitir(pedido);

            return this.pedidoDTOAssembler.toDTO(pedido);
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
