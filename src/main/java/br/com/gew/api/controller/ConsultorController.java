package br.com.gew.api.controller;

import br.com.gew.api.assembler.FuncionarioAssembler;
import br.com.gew.api.model.input.ConsultorInputDTO;
import br.com.gew.api.model.output.ConsultorOutputDTO;
import br.com.gew.api.model.output.FuncionarioDataOutputDTO;
import br.com.gew.api.model.output.FuncionarioOutputDTO;
import br.com.gew.domain.entities.Funcionario;
import br.com.gew.domain.services.FuncionariosService;
import br.com.gew.domain.utils.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/consultores")
public class ConsultorController {

    private FuncionarioAssembler funcionarioAssembler;
    private FuncionariosService funcionariosService;

    private CargosFuncionariosUtils cargosFuncionariosUtils;
    private ConsultoresFornecedoresUtils consultoresFornecedoresUtils;
    private SkillsUtils skillsUtils;
    private ConsultoresUtils consultoresUtils;
    private FuncionariosUtils funcionariosUtils;

    @PostMapping
    public ResponseEntity<FuncionarioDataOutputDTO> cadastrar(
            @RequestBody ConsultorInputDTO consultorInputDTO
    ) throws Exception {
        if (consultoresUtils.verifyExceptionCadastro(consultorInputDTO)) {
            return ResponseEntity.badRequest().build();
        }

        Funcionario novoFuncionario = funcionarioAssembler.toEntity(consultorInputDTO.getFuncionarioData());

        novoFuncionario.setSenha(
                new BCryptPasswordEncoder().encode(novoFuncionario.getSenha())
        );

        Funcionario funcionario = funcionariosService.cadastrar(novoFuncionario);

        cargosFuncionariosUtils.cadastrar(
                "Consultor", funcionario.getNumero_cracha()
        );

        consultoresFornecedoresUtils.cadastrar(
                consultorInputDTO.getFornecedor(), funcionario.getNumero_cracha()
        );

        skillsUtils.cadastrar(
                consultorInputDTO.getSkills(), funcionario.getNumero_cracha()
        );

        return ResponseEntity.ok(funcionarioAssembler.toModel(funcionario));
    }

    @GetMapping
    public ResponseEntity<List<ConsultorOutputDTO>> listar() throws Exception {
        return ResponseEntity.ok(consultoresUtils.listar());
    }

    @GetMapping("/{funcionario_cracha}")
    public ResponseEntity<ConsultorOutputDTO> buscar(
            @PathVariable long funcionario_cracha
    ) throws Exception {
        return ResponseEntity.ok(consultoresUtils.buscar(funcionario_cracha));
    }

    @PutMapping("/{funcionario_cracha}")
    public ResponseEntity<FuncionarioDataOutputDTO> editar(
            @RequestBody ConsultorInputDTO consultorInputDTO,
            @PathVariable long funcionario_cracha
    ) throws Exception {
        if (consultoresUtils.verifyExceptionEdicao(consultorInputDTO, funcionario_cracha)){
            return ResponseEntity.badRequest().build();
        }

        Funcionario novoFuncionario = funcionarioAssembler
                .toEntity(consultorInputDTO.getFuncionarioData());

        Funcionario funcionario = funcionariosService.editar(novoFuncionario, funcionario_cracha);

        consultoresFornecedoresUtils.cadastrar(
                consultorInputDTO.getFornecedor(), funcionario.getNumero_cracha()
        );

        skillsUtils.editar(
                consultorInputDTO.getSkills(), funcionario.getNumero_cracha()
        );

        return ResponseEntity.ok(funcionarioAssembler.toModel(funcionario));
    }

    @DeleteMapping("/{funcionario_cracha}")
    public ResponseEntity<FuncionarioDataOutputDTO> remover(
            @PathVariable long funcionario_cracha
    ) throws Exception {
        if (consultoresUtils.buscar(funcionario_cracha) == null) {
            return ResponseEntity.notFound().build();
        }

        cargosFuncionariosUtils.remover(funcionario_cracha);

        consultoresFornecedoresUtils.remover(funcionario_cracha);

        skillsUtils.remover(funcionario_cracha);

        return funcionariosUtils.remover(funcionario_cracha);
    }

}
