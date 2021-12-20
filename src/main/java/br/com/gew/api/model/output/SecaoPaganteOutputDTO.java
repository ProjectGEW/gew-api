package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecaoPaganteOutputDTO {

    private SecaoOutputDTO secao;
    private double percentual;
    private double valor;

}
