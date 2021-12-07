package br.com.gew.api.model.output;

import br.com.gew.domain.entities.Secao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecaoPaganteOutputDTO {

    private Secao secao;
    private double percentual;
    private double valor;

}
