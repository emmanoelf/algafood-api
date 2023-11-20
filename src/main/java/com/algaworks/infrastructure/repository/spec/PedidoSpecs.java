package com.algaworks.infrastructure.repository.spec;

import com.algaworks.domain.model.Pedido;
import com.algaworks.domain.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import javax.persistence.criteria.Predicate;

public class PedidoSpecs {
    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro){
        return((root, query, criteriaBuilder) -> {
            if(Pedido.class.equals(query.getResultType())){
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            ArrayList<Predicate> predicates = new ArrayList<>();

            if(filtro.getClienteId() != null){
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filtro.getClienteId()));
            }

            if(filtro.getRestauranteId() != null){
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filtro.getRestauranteId()));
            }

            if(filtro.getDataCriacaoInicio() != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
            }

            if(filtro.getDataCriacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
