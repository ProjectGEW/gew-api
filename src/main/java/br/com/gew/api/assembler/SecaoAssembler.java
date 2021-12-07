package br.com.gew.api.assembler;

import br.com.gew.api.model.output.SecaoOutputDTO;
import br.com.gew.domain.entities.Secao;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class SecaoAssembler {

    private ModelMapper modelMapper;

    public SecaoOutputDTO toModel(Secao secao) {
        return modelMapper.map(secao, SecaoOutputDTO.class);
    }

    public List<SecaoOutputDTO> toCollectionModel(List<Secao> secoes) {
        return secoes.stream().map(this::toModel).collect(Collectors.toList());
    }

}
