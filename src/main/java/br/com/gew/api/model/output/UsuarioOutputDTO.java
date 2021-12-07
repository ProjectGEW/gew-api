package br.com.gew.api.model.output;

/*
 * DTO para manter os dados do usuário
 * */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioOutputDTO {

    private String email;

    @JsonIgnore
    private String senha;
}
