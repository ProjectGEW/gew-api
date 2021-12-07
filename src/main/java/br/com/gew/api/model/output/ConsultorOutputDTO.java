package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultorOutputDTO {

    private FuncionarioDataOutputDTO funcionarioData;

    private List<Long> projetos;

    private List<String> skills;

    private String fornecedor;

    private boolean status;

}
