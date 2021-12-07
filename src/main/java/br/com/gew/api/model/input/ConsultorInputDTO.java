package br.com.gew.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ConsultorInputDTO {

    @NotNull
    private FuncionarioDataInputDTO funcionarioData;

    @NotBlank
    private String fornecedor;

    @NotNull
    private List<String> skills;

}
