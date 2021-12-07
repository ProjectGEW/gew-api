package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioOutputDTO {

    private FuncionarioDataOutputDTO funcionario;

    private String secao;

}
