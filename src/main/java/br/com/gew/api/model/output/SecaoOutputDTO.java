package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecaoOutputDTO {

    private String nome;
    private FuncionarioDataOutputDTO responsavel;

}
