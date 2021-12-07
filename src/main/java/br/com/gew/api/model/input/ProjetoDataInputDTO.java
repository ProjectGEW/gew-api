package br.com.gew.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProjetoDataInputDTO {

    @NotNull
    private long numeroDoProjeto;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotBlank
    private String ata;

    @NotBlank
    private long cracha_responsavel;

    @NotBlank
    private long cracha_solicitante;

    @NotNull
    private String data_de_inicio;

    @NotNull
    private String data_de_termino;

    @NotNull
    private String data_de_aprovacao;

}
