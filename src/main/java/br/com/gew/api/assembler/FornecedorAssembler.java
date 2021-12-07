package br.com.gew.api.assembler;

import br.com.gew.api.model.output.FornecedorOutputDTO;
import br.com.gew.domain.entities.Fornecedor;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class FornecedorAssembler {

    private ModelMapper modelMapper;

    public FornecedorOutputDTO toModel(Fornecedor fornecedor) {
        return modelMapper.map(fornecedor, FornecedorOutputDTO.class);
    }

    public List<FornecedorOutputDTO> toCollectionModel(List<Fornecedor> fornecedores) {
        return fornecedores.stream().map(this::toModel).collect(Collectors.toList());
    }

}
