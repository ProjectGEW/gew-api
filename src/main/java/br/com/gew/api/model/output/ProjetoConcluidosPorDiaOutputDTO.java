package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoConcluidosPorDiaOutputDTO {

    private String data;

    private int projetosConcluidos;

}
