package com.algaworks.domain.service;

import com.algaworks.domain.exception.NegocioException;
import com.algaworks.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.domain.model.*;
import com.algaworks.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private CadastroProdutoService cadastroProdutoService;

    public Pedido buscarOuFalhar(Long id){
        return this.pedidoRepository.findById(id).orElseThrow(() ->
                new PedidoNaoEncontradoException(id));
    }

    @Transactional
    public Pedido emitir(Pedido pedido){
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return this.pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido){
        Restaurante restaurante = this.cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        Usuario cliente = this.cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
        Cidade cidade = this.cadastroCidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        FormaPagamento formaPagamento = this.cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        if(!restaurante.aceitaFormaPagamento(formaPagamento)){
            throw new NegocioException(
                    String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                            formaPagamento.getDescricao()));
        }

        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEnderecoEntrega().setCidade(cidade);
    }

    private void validarItens(Pedido pedido){
        pedido.getItens().forEach(item -> {
            Produto produto = this.cadastroProdutoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setProduto(produto);
            item.setPedido(pedido);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
