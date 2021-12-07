package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoVerbaOutputDTO {

    private double verbaConcluidos;

    private double verbaEmAndamento;

    private double verbaAtrasados;

    private double verbaTotal;

}
