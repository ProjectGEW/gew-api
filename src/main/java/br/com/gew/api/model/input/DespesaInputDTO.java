package br.com.gew.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class DespesaInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    private int esforco;

    @NotNull
    private BigDecimal valor;

}
