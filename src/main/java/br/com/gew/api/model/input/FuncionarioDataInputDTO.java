package br.com.gew.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FuncionarioDataInputDTO {

    @NotNull
    private Long numero_cracha;

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @NotBlank
    private String telefone;

    @NotNull
    private double valor_hora;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

}
