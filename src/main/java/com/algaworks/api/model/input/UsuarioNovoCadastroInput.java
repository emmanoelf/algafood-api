package com.algaworks.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioNovoCadastroInput extends UsuarioInput{
    @NotBlank
    private String senha;
}
