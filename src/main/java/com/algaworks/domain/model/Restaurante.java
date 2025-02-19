package com.algaworks.domain.model;

import com.algaworks.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValorZeroIncluiDescricao(valorField="taxaFrete", descricaoField="nome", descricaoObrigatoria="Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(name="cozinha_id", nullable = false)
    private Cozinha cozinha;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @Column(name="ativo", nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @Column(name="aberto", nullable = false)
    private Boolean aberto = Boolean.FALSE;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name="restaurante_id"),
            inverseJoinColumns = @JoinColumn(name="usuario_id"))
    private Set<Usuario> responsaveis = new HashSet<>();

    public void ativar(){
        setAtivo(true);
    }

    public void inativar(){
        setAtivo(false);
    }

    public boolean desassociarFormaPagamento(FormaPagamento formaPagamento){
        return this.getFormasPagamento().remove(formaPagamento);
    }

    public boolean associarFormaPagamento(FormaPagamento formaPagamento){
        return this.getFormasPagamento().add(formaPagamento);
    }

    public void abrir(){
        this.setAberto(true);
    }

    public void fechar(){
        this.setAberto(false);
    }

    public void associarResponsavel(Usuario usuario){
        this.getResponsaveis().add(usuario);
    }

    public void desassociarResponsavel(Usuario usuario){
        this.getResponsaveis().remove(usuario);
    }

    public boolean aceitaFormaPagamento(FormaPagamento formaPagamento){
        return this.getFormasPagamento().contains(formaPagamento);
    }

    public boolean isAberto(){
        return this.aberto;
    }

    public boolean isFechado(){
        return !this.isAberto();
    }

    public boolean isAtivo(){
        return this.ativo;
    }

    public boolean isInativo(){
        return !this.isAtivo();
    }

    public boolean aberturaPermitida(){
        return this.isAtivo() && this.isFechado();
    }

    public boolean ativacaoPermitida(){
        return isInativo();
    }

    public boolean inativacaoPermitida(){
        return isAtivo();
    }

    public boolean fechamentoPermitido(){
        return this.isAberto();
    }
}
