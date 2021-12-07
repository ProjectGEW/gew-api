package br.com.gew.api.model.output;

/*
 * DTO para manter os dados do fornecedor
 *
 * valorTotalCcPagantes: valor total para bancar o projeto
 * valorTotalDespesas: valor total de custos
 * valorTotalEsforco: valor total de horas
 * */

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValoresTotaisOutputDTO {

    private double valorTotalCcPagantes;
    private double valorTotalDespesas;
    private double valorTotalEsforco;

}
