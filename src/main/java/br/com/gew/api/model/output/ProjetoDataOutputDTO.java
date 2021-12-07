package br.com.gew.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjetoDataOutputDTO {

    private long id;

    private long numeroDoProjeto;
    private String titulo;
    private String descricao;
    private String ata;

    private FuncionarioDataOutputDTO solicitante;
    private FuncionarioDataOutputDTO responsavel;

    private String data_de_inicio;
    private String data_de_termino;
    private String data_de_aprovacao;

    private String statusProjeto;
    private String secao;
    private int horas_apontadas;

}
