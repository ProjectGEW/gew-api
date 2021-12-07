package br.com.gew.api.assembler;

import br.com.gew.api.model.input.FuncionarioDataInputDTO;
import br.com.gew.api.model.output.FuncionarioDataOutputDTO;
import br.com.gew.domain.entities.Funcionario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class FuncionarioAssembler {

    private ModelMapper modelMapper;

    public Funcionario toEntity(FuncionarioDataInputDTO funcionarioData) {
        return modelMapper.map(funcionarioData, Funcionario.class);
    }

    public List<Funcionario> toCollectionEntity(List<FuncionarioDataInputDTO> funcionariosData) {
        return funcionariosData.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public FuncionarioDataOutputDTO toModel(Funcionario funcionario) {
        return modelMapper.map(funcionario, FuncionarioDataOutputDTO.class);
    }

    public List<FuncionarioDataOutputDTO> toCollectionModel(List<Funcionario> funcionarios) {
        return funcionarios.stream().map(this::toModel).collect(Collectors.toList());
    }

}
