package com.algaworks.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {
    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2024-03-02T20:34:04Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2024-03-02T20:37:04Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2024-03-02T22:04:04Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2024-03-02T20:34:04Z")
    private OffsetDateTime dataCancelamento;

    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
    private FormaPagamentoDTO formaPagamento;
    private EnderecoDTO endereco;
    private List<ItemPedidoDTO> itens;
}
