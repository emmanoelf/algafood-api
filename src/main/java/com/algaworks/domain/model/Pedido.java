package com.algaworks.domain.model;

import com.algaworks.domain.event.PedidoCanceladoEvent;
import com.algaworks.domain.event.PedidoConfirmadoEvent;
import com.algaworks.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;

    @Column(nullable = true)
    private OffsetDateTime dataConfirmacao;

    @Column(nullable = true)
    private OffsetDateTime dataCancelamento;

    @Column(nullable = true)
    private OffsetDateTime dataEntrega;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.CRIADO;

    @Embedded
    private Endereco enderecoEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name="usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void definirFrete(){
        this.setTaxaFrete(this.getRestaurante().getTaxaFrete());
    }

    public void atribuirItensAoPedido(){
        this.getItens().forEach(item -> item.setPedido(this));
    }

    public void calcularValorTotal(){
        this.getItens().forEach(ItemPedido::calcularPrecoTotal);
        this.subtotal = getItens().stream().map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.valorTotal = this.subtotal.add(this.taxaFrete);
    }

    public void entregar(){
        this.setStatus(StatusPedido.ENTREGUE);
        this.setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar(){
        this.setStatus(StatusPedido.CANCELADO);
        this.setDataCancelamento(OffsetDateTime.now());
        this.registerEvent(new PedidoCanceladoEvent(this));
    }

    public void confirmar(){
        this.setStatus(StatusPedido.CONFIRMADO);
        this.setDataConfirmacao(OffsetDateTime.now());
        this.registerEvent(new PedidoConfirmadoEvent(this));
    }

    private void setStatus(StatusPedido novoStatus){
        if(this.getStatus().naoPodeAlterarPara(novoStatus)){
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s",
                    this.getCodigo(),
                    this.getStatus().getDescricao(),
                    novoStatus.getDescricao()
            ));
        }

        this.status = novoStatus;
    }

    public boolean podeSerConfirmado(){
        return this.getStatus().podeAlterarPara(StatusPedido.CONFIRMADO);
    }

    public boolean podeSerEntregue(){
        return this.getStatus().podeAlterarPara(StatusPedido.ENTREGUE);
    }

    public boolean podeSerCancelado(){
        return this.getStatus().podeAlterarPara(StatusPedido.CANCELADO);
    }

    @PrePersist
    private void gerarCodigo(){
        this.setCodigo(UUID.randomUUID().toString());
    }
}
