package br.com.gew.api.controller;

import br.com.gew.api.assembler.ProjetoAssembler;
import br.com.gew.api.model.input.HorasInputDTO;
import br.com.gew.api.model.input.ProjetoInputDTO;
import br.com.gew.api.model.output.*;
import br.com.gew.domain.entities.Projeto;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.CargosFuncionariosService;
import br.com.gew.domain.services.CargosService;
import br.com.gew.domain.services.ProjetosService;
import br.com.gew.domain.utils.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private ProjetosService projetosService;

    private ProjetoAssembler projetoAssembler;

    private ProjetosUtils projetosUtils;
    private SecoesPagantesUtils secoesPagantesUtils;
    private DespesasUtils despesasUtils;
    private ProjetoContagemUtils projetoContagemUtils;
    private AlocadosUtils alocadosUtils;
    private VerbaUtils verbaUtils;

    @PostMapping
    public ResponseEntity<ProjetoDataOutputDTO> cadastrar(
            @RequestBody ProjetoInputDTO projetoInputDTO
    ) throws Exception {

        if (!projetosUtils.verifyExceptionCadastro(projetoInputDTO)) {
            return ResponseEntity.badRequest().build();
        }

        Projeto novoProjeto = projetosUtils.setDadosPadrao(projetoInputDTO.getProjetoData());

        Projeto projeto = projetosService.cadastrar(novoProjeto);

        double totalDespesas = despesasUtils.cadastrar(projetoInputDTO.getDespesas(), projeto.getNumeroDoProjeto());

        secoesPagantesUtils.cadastrar(projetoInputDTO.getSecoesPagantes(),
                projeto.getNumeroDoProjeto(), totalDespesas);

        return ResponseEntity.ok(projetoAssembler.toModel(novoProjeto));
    }

    @GetMapping
    public ResponseEntity<List<ProjetoOutputDTO>> listar() throws Exception {
        return ResponseEntity.ok(projetosUtils.listar());
    }

    @GetMapping("/count")
    public ResponseEntity<ContagemOutputDTO> contarPorStatus() throws ExceptionTratement {
        return ResponseEntity.ok(projetoContagemUtils.contar());
    }

    @GetMapping("/count/{dias}/{numeroDoProjeto}")
    public ResponseEntity<List<VerbaUtilizadaPorDiaOutputDTO>> calcularVerbaUtilizadaPorDia(
            @PathVariable int dias,
            @PathVariable long numeroDoProjeto
    ) throws ExceptionTratement {
        return ResponseEntity.ok(verbaUtils.calcularVerbaUtilizadaPorDia(dias, numeroDoProjeto));
    }

    @GetMapping("/count/verba/{numeroDoProjeto}")
    public ResponseEntity<VerbaUtilizadaOutputDTO> calcularVerbaUtilizada(
            @PathVariable long numeroDoProjeto
    ) throws ExceptionTratement {
        return ResponseEntity.ok(verbaUtils.contarVerbaUtilizada(numeroDoProjeto));
    }

    @GetMapping("/count/last-seven")
    public ResponseEntity<List<ProjetoConcluidosPorDiaOutputDTO>> contarUltimosDias() throws ExceptionTratement {
        return ResponseEntity.ok(projetoContagemUtils.concluidosUltimosDias());
    }

    @GetMapping("/horas/{numeroDoProjeto}")
    public ResponseEntity<List<HorasApontadasOutputDTO>> horasApontadas(
            @PathVariable long numeroDoProjeto
    ) throws ExceptionTratement {
        return ResponseEntity.ok(verbaUtils.horasApontadas(numeroDoProjeto));
    }

    @GetMapping("/{numeroDoProjeto}")
    public ResponseEntity<ProjetoOutputDTO> buscar(
            @PathVariable long numeroDoProjeto
    ) throws Exception {
        return projetosUtils.buscar(numeroDoProjeto);
    }

    @PutMapping("/{numeroDoProjeto}")
    public ResponseEntity<ProjetoDataOutputDTO> editar(
            @RequestBody ProjetoInputDTO projetoInputDTO,
            @PathVariable long numeroDoProjeto
    ) throws Exception {
        if (!projetosUtils.verifyExceptionEdicao(projetoInputDTO, numeroDoProjeto)) {
            return ResponseEntity.badRequest().build();
        }

        Projeto novoProjeto = projetosUtils.setDatabaseData(projetoInputDTO.getProjetoData(), numeroDoProjeto);

        Projeto projeto = projetosService.editar(novoProjeto, numeroDoProjeto);

        double totalDespesas = despesasUtils.editar(projetoInputDTO.getDespesas(), numeroDoProjeto);

        secoesPagantesUtils.editar(projetoInputDTO.getSecoesPagantes(), numeroDoProjeto, totalDespesas);

        return ResponseEntity.ok(projetoAssembler.toModel(projeto));
    }

    @DeleteMapping("/{numeroDoProjeto}")
    public ResponseEntity<ProjetoOutputDTO> remover(
            @PathVariable long numeroDoProjeto
    ) throws Exception {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            throw new EntityNotFoundException("Projeto não encontrado");
        }

        despesasUtils.remover(numeroDoProjeto);

        secoesPagantesUtils.remover(numeroDoProjeto);

        return projetosUtils.remover(numeroDoProjeto);
    }

    @PostMapping("/alocar/{numeroDoProjeto}/{funcionario_cracha}")
    public ResponseEntity<ProjetoOutputDTO> alocarConsultor(
            @PathVariable long numeroDoProjeto,
            @PathVariable long funcionario_cracha,
            @RequestBody HorasInputDTO horasInputDTO
    ) throws ExceptionTratement {
        if (alocadosUtils.verifyExceptionAlocacao(horasInputDTO.getHoras(), funcionario_cracha, numeroDoProjeto)) {
            return ResponseEntity.badRequest().build();
        }

        alocadosUtils.alocarConsultor(
                numeroDoProjeto, funcionario_cracha, horasInputDTO.getHoras()
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/horas/{numeroDoProjeto}/{funcionario_cracha}")
    public ResponseEntity<ProjetoOutputDTO> apontarHoras(
            @RequestBody HorasInputDTO horas,
            @PathVariable long numeroDoProjeto,
            @PathVariable long funcionario_cracha
    ) throws ExceptionTratement {
        if (alocadosUtils.verifyExceptionApontamento(funcionario_cracha, numeroDoProjeto)) {
            return ResponseEntity.badRequest().build();
        }

        projetosUtils.apontar(horas, numeroDoProjeto, funcionario_cracha);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/desalocar/{numeroDoProjeto}/{funcionario_cracha}")
    public ResponseEntity<ProjetoOutputDTO> desalocarConsultor(
            @PathVariable long numeroDoProjeto,
            @PathVariable long funcionario_cracha
    ) throws ExceptionTratement {
        if (alocadosUtils.verifyExceptionDesalocacao(funcionario_cracha, numeroDoProjeto)) {
            return ResponseEntity.badRequest().build();
        }

        alocadosUtils.desalocarConsultor(numeroDoProjeto, funcionario_cracha);

        return ResponseEntity.ok().build();
    }

}
