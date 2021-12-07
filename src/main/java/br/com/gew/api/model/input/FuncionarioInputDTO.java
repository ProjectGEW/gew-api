package br.com.gew.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioInputDTO {

    private FuncionarioDataInputDTO funcionario;

    private String secao;

    private String cargo;

}
