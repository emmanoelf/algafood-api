package com.algaworks.infrastructure.service;

import com.algaworks.domain.dto.VendaDiaria;
import com.algaworks.domain.filter.VendaDiariaFilter;
import com.algaworks.domain.model.Pedido;
import com.algaworks.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
    @Autowired
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        Root<Pedido> root = query.from(Pedido.class);

        Expression<Date> functionDateCricao = builder.function("date", Date.class, root.get("dataCriacao"));

        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
                functionDateCricao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection);
        query.groupBy(functionDateCricao);

        return manager.createQuery(query).getResultList();
    }
}
