package br.com.gew.api.assembler;

import br.com.gew.api.model.input.SecaoPaganteInputDTO;
import br.com.gew.api.model.output.SecaoPaganteOutputDTO;
import br.com.gew.domain.entities.SecaoPagante;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class SecaoPaganteAssembler {

    private ModelMapper modelMapper;

    public SecaoPagante toEntity(SecaoPaganteInputDTO secaoPaganteInputDTO) {
        return modelMapper.map(secaoPaganteInputDTO, SecaoPagante.class);
    }

    public List<SecaoPagante> toCollectionEntity(List<SecaoPaganteInputDTO> secoesPagantesInputDTO) {
        return secoesPagantesInputDTO.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public SecaoPaganteOutputDTO toModel(SecaoPagante secaoPagante) {
        return modelMapper.map(secaoPagante, SecaoPaganteOutputDTO.class);
    }

    public List<SecaoPaganteOutputDTO> toCollectionModel(List<SecaoPagante> secoesPagantes) {
        return secoesPagantes.stream().map(this::toModel).collect(Collectors.toList());
    }

}
