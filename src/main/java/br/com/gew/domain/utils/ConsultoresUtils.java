package br.com.gew.domain.utils;

import br.com.gew.api.assembler.FuncionarioAssembler;
import br.com.gew.api.model.input.ConsultorInputDTO;
import br.com.gew.api.model.output.ConsultorOutputDTO;
import br.com.gew.domain.entities.*;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ConsultoresUtils {

    private FuncionarioAssembler funcionarioAssembler;

    private FuncionariosService funcionariosService;
    private CargosFuncionariosService cargosFuncionariosService;
    private ConsultoresFornecedoresService consultoresFornecedoresService;
    private ConsultoresSkillsService consultoresSkillsService;
    private FornecedoresService fornecedoresService;
    private SkillsService skillsService;
    private AlocadosService alocadosService;
    private ProjetosService projetosService;

    private FuncionariosUtils funcionariosUtils;

    public List<ConsultorOutputDTO> listar() throws ExceptionTratement {
        List<ConsultorOutputDTO> consultoresOutput = new ArrayList<>();

        List<CargoFuncionario> consultoresCracha = cargosFuncionariosService.listarPorCargo(4);

        for (CargoFuncionario cargoFuncionario : consultoresCracha) {
            consultoresOutput.add(montarOutput(cargoFuncionario.getFuncionario_cracha()));
        }

        return consultoresOutput;
    }

    public ConsultorOutputDTO buscar(long funcionario_cracha) throws ExceptionTratement {
        if (funcionariosService.buscar(funcionario_cracha).isEmpty()) {
            throw new ExceptionTratement("Consultor não encontrado");
        }

        return montarOutput(funcionario_cracha);
    }

    private ConsultorOutputDTO montarOutput(long funcionario_cracha) throws ExceptionTratement {
        ConsultorOutputDTO consultorOutput = new ConsultorOutputDTO();

        setFuncionarioData(consultorOutput, funcionario_cracha);

        setProjetos(consultorOutput, funcionario_cracha);

        setFornecedor(consultorOutput, funcionario_cracha);

        setSkills(consultorOutput, funcionario_cracha);

        return consultorOutput;
    }

    private void setFuncionarioData(
            ConsultorOutputDTO consultorOutputDTO,
            long funcionario_cracha
    ) throws ExceptionTratement {
        Funcionario consultor = funcionariosService.buscar(funcionario_cracha).get();

        consultorOutputDTO.setFuncionarioData(funcionarioAssembler.toModel(consultor));
    }

    private void setProjetos(
            ConsultorOutputDTO consultorOutputDTO,
            long funcionario_cracha
    ) {
        consultorOutputDTO.setStatus(false);

        if (!alocadosService.listarPorFuncionario(funcionario_cracha).isEmpty()) {
            List<Alocado> alocados = alocadosService.listarPorFuncionario(funcionario_cracha);
            List<Long> projetos = new ArrayList<>();

            for (Alocado alocado : alocados) {
                projetos.add(
                        projetosService.buscar(alocado.getProjeto_id()).get().getNumeroDoProjeto()
                );
            }

            consultorOutputDTO.setProjetos(projetos);
            consultorOutputDTO.setStatus(true);
        }
    }

    private void setFornecedor(
            ConsultorOutputDTO consultorOutputDTO,
            long funcionario_cracha
    ) throws ExceptionTratement {
        Fornecedor fornecedor = fornecedoresService.buscar(
                consultoresFornecedoresService.buscarPorFuncionario(
                        funcionario_cracha
                ).getFornecedor_id()
        );

        consultorOutputDTO.setFornecedor(fornecedor.getNome());
    }

    private void setSkills(
            ConsultorOutputDTO consultorOutputDTO,
            long funcionario_cracha
    ) throws ExceptionTratement {
        List<String> skills = new ArrayList<>();
        List<ConsultorSkill> consultorSkills = consultoresSkillsService
                .listarPorConsultor(funcionario_cracha);

        for (ConsultorSkill consultorSkill : consultorSkills) {
            skills.add(skillsService.buscar(consultorSkill.getSkill_id()).get().getNome());
        }

        consultorOutputDTO.setSkills(skills);
    }

    public boolean verifyExceptionCadastro(ConsultorInputDTO consultorInputDTO) throws ExceptionTratement {
        funcionariosUtils.verifyFuncionarioInfo(consultorInputDTO.getFuncionarioData());

        verifyFornecedor(consultorInputDTO.getFornecedor());

        verifySkills(consultorInputDTO.getSkills());

        return false;
    }

    public boolean verifyExceptionEdicao(
            ConsultorInputDTO consultorInputDTO,
            long funcionario_cracha
    ) throws ExceptionTratement {
        if (funcionariosService.buscar(funcionario_cracha).isEmpty()) {
            throw new ExceptionTratement("Consultor não encontrado");
        }

        funcionariosUtils.verifyFuncionarioInfoEdit(
                consultorInputDTO.getFuncionarioData(), funcionario_cracha
        );

        verifySkillsEdit(consultorInputDTO.getSkills(), funcionario_cracha);

        return false;
    }

    private void verifyFornecedor(String fornecedor) throws ExceptionTratement {
        if (fornecedoresService.buscarPorNome(
                fornecedor
        ) == null) {
            throw new ExceptionTratement("Fornecedor informado não encontrado");
        }
    }

    private void verifySkills(List<String> skillsInput) throws ExceptionTratement {
        String[] skills = new String[skillsInput.size()];

        if (skillsInput.size() > 1) {
            for (int i = 0; i < skillsInput.size(); i ++) {
                skills[i] = skillsInput.get(i);
            }

            for (int i = 1; i < skillsInput.size(); i ++) {
                for (int j = 1; j < i + 1; j ++) {
                    if (skills[i].equals(skills[j - 1])) {
                        throw new ExceptionTratement("Skill já informada anteriormente");
                    }
                }
            }
        }
    }

    private void verifySkillsEdit(
            List<String> skillsInput,
            long funcionario_cracha
    ) throws ExceptionTratement {
        List<ConsultorSkill> consultorSkills = consultoresSkillsService.listarPorConsultor(funcionario_cracha);

        if (skillsInput.size() < consultorSkills.size()) {
            throw new ExceptionTratement("Não pode informar menos skills do que o consultor já tinha");
        }
    }

}
