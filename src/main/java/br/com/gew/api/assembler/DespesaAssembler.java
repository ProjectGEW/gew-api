package br.com.gew.api.assembler;

import br.com.gew.api.model.input.DespesaInputDTO;
import br.com.gew.api.model.output.DespesaOutputDTO;
import br.com.gew.domain.entities.Despesa;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class DespesaAssembler {

    private ModelMapper modelMapper;

    public Despesa toEntity(DespesaInputDTO despesaInputDTO) {
        return modelMapper.map(despesaInputDTO, Despesa.class);
    }

    public List<Despesa> toCollectionEntity(List<DespesaInputDTO> despesasInputDTO) {
        return despesasInputDTO.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public DespesaOutputDTO toModel(Despesa despesa) {
        return modelMapper.map(despesa, DespesaOutputDTO.class);
    }

    public List<DespesaOutputDTO> toCollectionModel(List<Despesa> despesas) {
        return despesas.stream().map(this::toModel).collect(Collectors.toList());
    }

}
