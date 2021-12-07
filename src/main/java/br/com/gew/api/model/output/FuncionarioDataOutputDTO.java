package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDataOutputDTO {

    private long numero_cracha;

    private String nome;

    private String email;

    private double valor_hora;

}
