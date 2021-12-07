package br.com.gew.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProjetoInputDTO {

    @NotNull
    private ProjetoDataInputDTO projetoData;

    @NotNull
    private List<DespesaInputDTO> despesas;

    @NotNull
    private List<SecaoPaganteInputDTO> secoesPagantes;

}
