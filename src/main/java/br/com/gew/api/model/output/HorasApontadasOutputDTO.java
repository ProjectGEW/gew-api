package br.com.gew.api.model.output;

/*
 * DTO para retornar ao front dados
 * de autenticação do usuário
 * */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorasApontadasOutputDTO {

    private double horas_apontadas;

    private String data;

    private String nome_funcionario;

    private double valor_hora;

}
