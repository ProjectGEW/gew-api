package br.com.gew.api.assembler;

/*
 * Classe responsável por mapear os dados de
 * de DTO's para Entidade ou vice-versa
 * relacionadas aos usuários
 *
 * modelMapper: biblioteca de mapeamento de dados
 * */

import br.com.gew.api.model.input.UsuarioInputDTO;
import br.com.gew.api.model.output.UsuarioOutputDTO;
import br.com.gew.domain.entities.Funcionario;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UsuarioAssembler {

    private ModelMapper modelMapper;

    public UsuarioOutputDTO toModel(Funcionario usuario){
        return modelMapper.map(usuario, UsuarioOutputDTO.class);
    }

    public Funcionario toEntity(UsuarioInputDTO usuarioInputDTO){
        return modelMapper.map(usuarioInputDTO, Funcionario.class);
    }

    public List<UsuarioOutputDTO> toCollectionModel(List<Funcionario> usuarios){
        return usuarios.stream().map(this::toModel).collect(Collectors.toList());
    }

}
