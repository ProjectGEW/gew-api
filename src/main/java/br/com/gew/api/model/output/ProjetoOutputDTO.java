package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjetoOutputDTO {

    private ProjetoDataOutputDTO projetoData;

    private List<DespesaOutputDTO> despesas;

    private List<SecaoPaganteOutputDTO> secoesPagantes;

    private ValoresTotaisOutputDTO valoresTotais;

}
