package br.com.gew.domain.utils;

import br.com.gew.api.assembler.FuncionarioAssembler;
import br.com.gew.api.model.input.FuncionarioDataInputDTO;
import br.com.gew.api.model.input.FuncionarioInputDTO;
import br.com.gew.api.model.output.FuncionarioDataOutputDTO;
import br.com.gew.api.model.output.FuncionarioOutputDTO;
import br.com.gew.domain.entities.Funcionario;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.CargosFuncionariosService;
import br.com.gew.domain.services.CargosService;
import br.com.gew.domain.services.FuncionariosService;
import br.com.gew.domain.services.SecoesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FuncionariosUtils {

    private FuncionariosService funcionariosService;
    private CargosService cargosService;
    private SecoesService secoesService;

    private CargosFuncionariosService cargosFuncionariosService;

    private FuncionarioAssembler funcionarioAssembler;

    public FuncionarioOutputDTO buscar(long numeroCracha) {
        Funcionario funcionario = funcionariosService.buscar(numeroCracha).get();

        FuncionarioOutputDTO funcionarioOutputDTO = new FuncionarioOutputDTO();

        funcionarioOutputDTO.setFuncionario(funcionarioAssembler.toModel(funcionario));
        funcionarioOutputDTO.setSecao(secoesService.buscarPorFuncionario(numeroCracha).get().getNome());

        return funcionarioOutputDTO;
    }

    public List<FuncionarioOutputDTO> listar() {
        List<FuncionarioOutputDTO> funcionarioOutputs = new ArrayList<>();
        List<Funcionario> funcionarios = funcionariosService.listar();

        for (Funcionario funcionario : funcionarios) {
            if (cargosFuncionariosService.buscarPorFuncionario(
                    funcionario.getNumero_cracha()
            ).getCargo_id() != 4) {
                FuncionarioOutputDTO funcionarioOutputDTO = new FuncionarioOutputDTO();

                funcionarioOutputDTO.setFuncionario(funcionarioAssembler.toModel(funcionario));
                funcionarioOutputDTO.setSecao(
                        secoesService.buscarPorFuncionario(funcionario.getNumero_cracha()).get().getNome()
                );

                funcionarioOutputs.add(funcionarioOutputDTO);
            }
        }

        return funcionarioOutputs;
    }

    public boolean verifyExceptionCadastro(
            FuncionarioInputDTO funcionarioInputDTO
    ) throws ExceptionTratement {
        verifyFuncionarioInfo(funcionarioInputDTO.getFuncionario());

        verifyCargo(funcionarioInputDTO.getCargo());

        verifySecao(funcionarioInputDTO.getSecao());

        return false;
    }

    public boolean verifyExceptionEdicao(
            FuncionarioInputDTO funcionarioInputDTO, long numeroCracha
    ) throws ExceptionTratement {
        if (funcionariosService.buscar(numeroCracha).isEmpty()) {
            throw new ExceptionTratement("Funcionário não encontrado");
        }

        verifyFuncionarioInfoEdit(funcionarioInputDTO.getFuncionario(), numeroCracha);

        verifyCargo(funcionarioInputDTO.getCargo());

        verifySecao(funcionarioInputDTO.getSecao());

        return false;
    }

    public void verifyFuncionarioInfo(
            FuncionarioDataInputDTO funcionarioDataInputDTO
    ) throws ExceptionTratement {
        if (funcionariosService.buscar(
                funcionarioDataInputDTO.getNumero_cracha()
        ).isPresent()) {
            throw new ExceptionTratement("Funcionário com esse crachá já cadastrado");
        }

        if (funcionariosService.buscarPorCpf(
                funcionarioDataInputDTO.getCpf()
        ).isPresent()) {
            throw new ExceptionTratement("Funcionário com esse cpf já cadastrado");
        }

        if (funcionariosService.buscarPorTelefone(
                funcionarioDataInputDTO.getTelefone()
        ).isPresent()) {
            throw new ExceptionTratement("Funcionário com esse telefone já cadastrado");
        }

        if (funcionariosService.buscarPorEmail(
                funcionarioDataInputDTO.getEmail()
        ).isPresent()) {
            throw new ExceptionTratement("Funcionário com esse email já cadastrado");
        }
    }

    public void verifyFuncionarioInfoEdit(
            FuncionarioDataInputDTO funcionarioDataInputDTO,
            long numeroCracha
    ) throws ExceptionTratement {
        if (funcionarioDataInputDTO.getNumero_cracha() !=
                funcionariosService.buscar(numeroCracha).get().getNumero_cracha()
        ) {
            boolean numeroCrachaValidation = funcionariosService.buscar(
                    funcionarioDataInputDTO.getNumero_cracha()).isPresent();

            if (numeroCrachaValidation) {
                throw new ExceptionTratement("Funcionário com este crachá já cadastrado");
            }
        }

        if (!funcionarioDataInputDTO.getCpf()
                .equals(funcionariosService.buscar(numeroCracha).get().getCpf())
        ) {
            boolean cpfValidation = funcionariosService.buscarPorCpf(
                    funcionarioDataInputDTO.getCpf()).isPresent();

            if (cpfValidation) {
                throw new ExceptionTratement("Funcionário com este cpf já cadastrado");
            }
        }

        if (!funcionarioDataInputDTO.getTelefone()
                .equals(funcionariosService.buscar(numeroCracha).get().getTelefone())
        ) {
            boolean telefoneValidation = funcionariosService.buscarPorTelefone(
                    funcionarioDataInputDTO.getTelefone()).isPresent();

            if (telefoneValidation) {
                throw new ExceptionTratement("Funcionário com este telefone já cadastrado");
            }
        }

        if (!funcionarioDataInputDTO.getEmail()
                .equals(funcionariosService.buscar(numeroCracha).get().getEmail())
        ) {
            boolean emailValidation = funcionariosService.buscarPorEmail(
                    funcionarioDataInputDTO.getEmail()).isPresent();

            if (emailValidation) {
                throw new ExceptionTratement("Funcionário com este email já cadastrado");
            }
        }
    }

    private void verifyCargo(String cargo) throws ExceptionTratement {
        if (cargo.equalsIgnoreCase("CONSULTOR")) {
            throw new ExceptionTratement("Use a rota de cadastro de consultor para isso");
        }

        if (cargosService.buscarPorNome(
                "ROLE_" + cargo.toUpperCase()
        ) == null) {
            throw new ExceptionTratement("Cargo informado não existe");
        }
    }

    private void verifySecao(String secao) throws ExceptionTratement {
        if (secoesService.buscarPorNome(
                secao
        ) == null) {
            throw new ExceptionTratement("Seção informada não encontrada");
        }
    }

    public ResponseEntity<FuncionarioDataOutputDTO> remover(long numeroCracha) throws ExceptionTratement {
        if (funcionariosService.buscar(numeroCracha).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        funcionariosService.remover(numeroCracha);

        return ResponseEntity.ok().build();
    }

}
