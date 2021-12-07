package br.com.gew.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class SecaoPaganteInputDTO {

    @NotNull
    private long secao_id;

    @NotNull
    private BigDecimal valor;

}
