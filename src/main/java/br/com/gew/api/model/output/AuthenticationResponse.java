package br.com.gew.api.model.output;

/*
 * DTO para retornar ao front dados
 * de autenticação do usuário
 * */

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {

    private String jwt;

    private UsuarioOutputDTO usuario;

    private String nome;

}
