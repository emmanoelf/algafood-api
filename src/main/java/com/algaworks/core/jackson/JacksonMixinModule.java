package com.algaworks.core.jackson;

import com.algaworks.api.model.mixin.CidadeMixin;
import com.algaworks.api.model.mixin.CozinhaMixin;
import com.algaworks.api.model.mixin.RestauranteMixin;
import com.algaworks.domain.model.Cidade;
import com.algaworks.domain.model.Cozinha;
import com.algaworks.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {
    public JacksonMixinModule(){
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
    }
}
