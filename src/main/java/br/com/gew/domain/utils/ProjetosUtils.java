package br.com.gew.domain.utils;

import br.com.gew.api.assembler.ProjetoAssembler;
import br.com.gew.api.model.input.*;
import br.com.gew.api.model.output.DespesaOutputDTO;
import br.com.gew.api.model.output.ProjetoOutputDTO;
import br.com.gew.api.model.output.SecaoPaganteOutputDTO;
import br.com.gew.api.model.output.ValoresTotaisOutputDTO;
import br.com.gew.domain.entities.*;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class ProjetosUtils {

    private ProjetoAssembler projetoAssembler;

    private SecoesService secoesService;
    private ProjetosService projetosService;
    private FuncionariosService funcionariosService;
    private DespesasService despesasService;
    private AlocadosService alocadosService;
    private AlocadosLogsService alocadosLogsService;
    private LogHorasService logHorasService;

    private DespesasUtils despesasUtils;
    private SecoesPagantesUtils secoesPagantesUtils;
    private LogHorasUtils logHorasUtils;

    public Projeto setDadosPadrao(ProjetoDataInputDTO projetoDataInputDTO) throws ExceptionTratement {
        Projeto projeto = projetoAssembler.toEntity(projetoDataInputDTO);

        projeto.setStatusProjeto(StatusProjeto.NAO_INICIADO);
        projeto.setDataDoCadastro(LocalDate.now());
        projeto.setHoras_apontadas(0);

        setResponsaveis(projetoDataInputDTO, projeto);

        return projeto;
    }

    public Projeto setDatabaseData(
            ProjetoDataInputDTO projetoDataInputDTO,
            long numeroDoProjeto
    ) throws ExceptionTratement {
        Projeto projeto = projetoAssembler.toEntity(projetoDataInputDTO);
        Projeto projetoDB = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get();

        projeto.setStatusProjeto(projetoDB.getStatusProjeto());
        projeto.setDataDoCadastro(projetoDB.getDataDoCadastro());
        projeto.setHoras_apontadas(projetoDB.getHoras_apontadas());

        setResponsaveis(projetoDataInputDTO, projeto);

        return projeto;
    }

    private void setResponsaveis(
            ProjetoDataInputDTO projetoDataInputDTO,
            Projeto projeto
    ) throws ExceptionTratement {
        projeto.setResponsavel(funcionariosService.buscar(
                projetoDataInputDTO.getCracha_responsavel()
        ).get());
        projeto.setSolicitante(funcionariosService.buscar(
                projetoDataInputDTO.getCracha_solicitante()
        ).get());
        projeto.setSecao(
                secoesService.buscarPorFuncionario(projetoDataInputDTO.getCracha_solicitante()).get().getNome()
        );
    }

    public List<ProjetoOutputDTO> listar() throws ExceptionTratement {
        List<ProjetoOutputDTO> projetoOutputDTOS = new ArrayList<>();

        List<Projeto> projetos = projetosService.listar();

        for (Projeto projeto : projetos) {
            projetoOutputDTOS.add(montarProjeto(projeto));
        }

        return projetoOutputDTOS;
    }

    public ResponseEntity<ProjetoOutputDTO> buscar(long numeroDoProjeto) throws ExceptionTratement {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(montarProjeto(
                projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get())
        );
    }

    public ResponseEntity<ProjetoOutputDTO> remover(long numeroDoProjeto) throws ExceptionTratement {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        projetosService.remover(
                projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId()
        );

        return ResponseEntity.ok().build();
    }

    private ProjetoOutputDTO montarProjeto(Projeto projeto) throws ExceptionTratement {
        ProjetoOutputDTO projetoOutputDTO = new ProjetoOutputDTO();

        projetoOutputDTO.setProjetoData(
                projetoAssembler.toModel(projeto)
        );

        projetoOutputDTO.setDespesas(
                despesasUtils.listar(projeto.getId())
        );

        projetoOutputDTO.setSecoesPagantes(
                secoesPagantesUtils.listar(projeto.getId())
        );

        projetoOutputDTO.setValoresTotais(calculaValores(projetoOutputDTO, projeto.getId()));

        return projetoOutputDTO;
    }

    private ValoresTotaisOutputDTO calculaValores(ProjetoOutputDTO projetoOutputDTO, long projetoId) {
        double valorTotalDespesas = 0;
        double valorTotalEsforcos = 0;
        double valorTotalCC = 0;

        ValoresTotaisOutputDTO valoresTotaisDTO = new ValoresTotaisOutputDTO();

        for (DespesaOutputDTO despesa : projetoOutputDTO.getDespesas()) {
            valorTotalDespesas += despesa.getValor();
            valorTotalEsforcos += despesa.getEsforco();
        }

        for (SecaoPaganteOutputDTO secaoPagante : projetoOutputDTO.getSecoesPagantes()) {
            valorTotalCC += secaoPagante.getValor();
        }

        valoresTotaisDTO.setValorTotalDespesas(valorTotalDespesas);
        valoresTotaisDTO.setValorTotalEsforco(valorTotalEsforcos);
        valoresTotaisDTO.setValorTotalCcPagantes(valorTotalCC);
        valoresTotaisDTO.setVerbaUtilizada(calculaVerbaUtilizada(projetoId));

        return valoresTotaisDTO;
    }

    private double calculaVerbaUtilizada(long projetoId) {
        double total = 0;
        List<Alocado> alocados = alocadosService.listarPorProjeto(projetoId);

        for (Alocado alocado : alocados) {
            Funcionario funcionario = funcionariosService.buscar(alocado.getFuncionario_cracha()).get();
            List<AlocadoLog> alocadoLogs = alocadosLogsService.listarPorAlocado(alocado.getId());

            for (AlocadoLog alocadoLog : alocadoLogs) {
                total += logHorasService.buscar(alocadoLog.getLog_id()).get().getHoras() *
                        funcionario.getValor_hora();
            }
        }

        return total;
    }

    public boolean verifyExceptionCadastro(
            ProjetoInputDTO projetoInputDTO
    ) throws ExceptionTratement {
        verifyAlreadyExists(projetoInputDTO.getProjetoData().getNumeroDoProjeto(),
                projetoInputDTO.getProjetoData().getTitulo());

        verifyResponsavel(projetoInputDTO.getProjetoData().getCracha_responsavel(),
                projetoInputDTO.getProjetoData().getCracha_solicitante());

        verifyDatas(projetoInputDTO.getProjetoData().getData_de_inicio(),
                projetoInputDTO.getProjetoData().getData_de_termino());

        int somaDespesas = verifyDespesas(projetoInputDTO.getDespesas());
        int somaSecoesPagantes = verifySecoesPagantes(projetoInputDTO.getSecoesPagantes());

        verifyValores(somaDespesas, somaSecoesPagantes);

        return true;
    }

    public boolean verifyExceptionEdicao(
            ProjetoInputDTO projetoInputDTO, long numeroDoProjeto
    ) throws ExceptionTratement {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            throw new EntityNotFoundException("Projeto não encontrado");
        }

        long projetoId = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();

        verifyEditAlreadyExists(projetoInputDTO.getProjetoData().getNumeroDoProjeto(),
                projetoInputDTO.getProjetoData().getTitulo(), projetoId);

        verifyResponsavel(projetoInputDTO.getProjetoData().getCracha_responsavel(),
                projetoInputDTO.getProjetoData().getCracha_solicitante());

        verifyDatas(projetoInputDTO.getProjetoData().getData_de_inicio(),
                projetoInputDTO.getProjetoData().getData_de_termino());

        int somaDespesas = verifyDespesas(projetoInputDTO.getDespesas());
        int somaSecoesPagantes = verifySecoesPagantes(projetoInputDTO.getSecoesPagantes());

        verifyValores(somaDespesas, somaSecoesPagantes);

        return true;
    }

    private void verifyAlreadyExists(
            long numeroDoProjeto,
            String titulo
    ) throws ExceptionTratement {
        boolean numeroDoProjetoValidation = projetosService.buscarPorNumeroProjeto(
                numeroDoProjeto
        ).isPresent();

        if (numeroDoProjetoValidation) {
            throw new ExceptionTratement("Projeto com este número já cadastrado");
        }

        boolean tituloValidation = projetosService.buscarPorTitulo(
                titulo).isPresent();

        if (tituloValidation) {
            throw new ExceptionTratement("Projeto com este título já cadastrado");
        }
    }

    private void verifyEditAlreadyExists(
            long numeroDoProjeto,
            String titulo,
            long projetoId
    ) throws ExceptionTratement {
        if (numeroDoProjeto != projetosService.buscar(projetoId).get().getNumeroDoProjeto()) {
            boolean numeroDoProjetoValidation = projetosService.buscarPorNumeroProjeto(
                    numeroDoProjeto).isPresent();

            if (numeroDoProjetoValidation) {
                throw new ExceptionTratement("Projeto com este número já cadastrado");
            }
        }

        if (!Objects.equals(titulo, projetosService.buscar(projetoId).get().getTitulo())) {
            boolean tituloValidation = projetosService.buscarPorTitulo(
                    titulo).isPresent();

            if (tituloValidation) {
                throw new ExceptionTratement("Projeto com este título já cadastrado");
            }
        }
    }

    private void verifyResponsavel(long responsavel, long solicitante) throws ExceptionTratement {
        if (responsavel == solicitante) {
            throw new ExceptionTratement("O responsável não pode ser o mesmo que solicitou");
        }
    }

    private void verifyDatas(String dataDeInicio, String dataDeTermino) throws ExceptionTratement {
        String[] data_inicio = sliceDate(dataDeInicio);
        String[] data_termino = sliceDate(dataDeTermino);

        if (Integer.parseInt(data_inicio[2]) > Integer.parseInt(data_termino[2])) {
            throw new ExceptionTratement("Ano de termino anterior ao de inicio");
        }

        if (Integer.parseInt(data_inicio[1]) > Integer.parseInt(data_termino[1])) {
            throw new ExceptionTratement("Mês de termino anterior ao de inicio");
        }

        if (Integer.parseInt(data_inicio[0]) > Integer.parseInt(data_termino[0])) {
            throw new ExceptionTratement("Dia do termino anterior ao de inicio");
        }
    }

    private int verifyDespesas(List<DespesaInputDTO> despesasInputDTOS) throws ExceptionTratement {
        int somaDespesas = 0;
        String[] despesas = new String[despesasInputDTOS.size()];

        for (int i = 0; i < despesasInputDTOS.size(); i ++) {
            despesas[i] = despesasInputDTOS.get(i).getNome();
            somaDespesas += despesasInputDTOS.get(i).getValor().intValue();
        }

        if (despesasInputDTOS.size() > 1) {
            for (int i = 1; i < despesasInputDTOS.size(); i ++) {
                for (int j = 1; j < i + 1; j ++) {
                    if (despesas[i].equals(despesas[j - 1])) {
                        throw new ExceptionTratement("Despesa já informada anteriormente");
                    }
                }
            }
        }

        return somaDespesas;
    }

    private int verifySecoesPagantes(
            List<SecaoPaganteInputDTO> secaoPaganteInputDTOS
    ) throws ExceptionTratement {
        int somaCcPagantes = 0;

        String[] ccPagantes = new String[secaoPaganteInputDTOS.size()];

        for (int i = 0; i < secaoPaganteInputDTOS.size(); i ++) {
            ccPagantes[i] = secaoPaganteInputDTOS.get(i).getSecao_nome();
            somaCcPagantes += secaoPaganteInputDTOS.get(i).getValor().intValue();
        }

        if (secaoPaganteInputDTOS.size() > 1) {
            for (int i = 1; i < secaoPaganteInputDTOS.size(); i ++) {
                for (int j = 1; j < i; j ++) {
                    if (ccPagantes[i] == ccPagantes[j - 1]) {
                        throw new ExceptionTratement("Secao já informada anteriormente");
                    }
                }
            }
        }

        return somaCcPagantes;
    }

    private void verifyValores(int somaDespesas, int somaCcPagantes) throws ExceptionTratement {
        if (somaCcPagantes < somaDespesas) {
            throw new ExceptionTratement("Verba dos ccPagantes menor do que a necessária");
        }

        if (somaCcPagantes > somaDespesas) {
            throw new ExceptionTratement("Verba dos ccPagantes maior do que a necessária");
        }
    }

    private String[] sliceDate(String date) {
        return date.split("/");
    }

    public void apontar(HorasInputDTO horas, long numeroDoProjeto, long numero_cracha) {
        if (projetosService.buscarPorNumeroProjeto(numeroDoProjeto).isEmpty()) {
            throw new EntityNotFoundException("Projeto não existe");
        }

        long projetoId = projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId();
        int horas_totais = horas.getHoras() + projetosService.buscar(projetoId).get().getHoras_apontadas();
        int horas_aprovadas = calculaHorasAprovadas(projetoId);

        if (horas.getHoras() + projetosService.buscar(projetoId).get().getHoras_apontadas() > horas_aprovadas) {
            throw new ExceptionTratement("Horas apontadas excedem as horas totais aprovadas");
        }

        Projeto projeto = projetosService.buscar(projetoId).get();

        projeto.setStatusProjeto(StatusProjeto.EM_ANDAMENTO);
        projeto.setHoras_apontadas(
                projetosService.buscar(projetoId).get().getHoras_apontadas() + horas.getHoras());

        if (horas_totais == horas_aprovadas) {
            projeto.setDataDaConclusao(LocalDate.now());
            projeto.setStatusProjeto(StatusProjeto.CONCLUIDO);
        }

        logHorasUtils.apontar(horas, projetoId, numero_cracha);
        projetosService.editar(projeto, numeroDoProjeto);
    }

    private int calculaHorasAprovadas(long projetoId) {
        int horas_aprovadas = 0;

        List<Despesa> despesasDoProjeto = despesasService.listarPorProjeto(projetoId);

        for (Despesa despesas : despesasDoProjeto) {
            horas_aprovadas += despesas.getEsforco();
        }

        return horas_aprovadas;
    }

}
