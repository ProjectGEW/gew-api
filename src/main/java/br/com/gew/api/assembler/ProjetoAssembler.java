package br.com.gew.api.assembler;

import br.com.gew.api.model.input.ProjetoDataInputDTO;
import br.com.gew.api.model.output.ProjetoDataOutputDTO;
import br.com.gew.domain.entities.Projeto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProjetoAssembler {

    private ModelMapper modelMapper;

    public Projeto toEntity(ProjetoDataInputDTO projetoDataInputDTO) {
        return modelMapper.map(projetoDataInputDTO, Projeto.class);
    }

    public ProjetoDataOutputDTO toModel(Projeto projeto) {
        return modelMapper.map(projeto, ProjetoDataOutputDTO.class);
    }

    public List<ProjetoDataOutputDTO> toCollectionModel(List<Projeto> projetos) {
        return projetos.stream().map(this::toModel).collect(Collectors.toList());
    }

}
