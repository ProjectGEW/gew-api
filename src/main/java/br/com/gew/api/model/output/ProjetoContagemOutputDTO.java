package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoContagemOutputDTO {

    private int concluidos;

    private int emAndamento;

    private int atrasados;

    private int total;

}
