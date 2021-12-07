package br.com.gew.domain.utils;

import br.com.gew.api.model.output.ContagemOutputDTO;
import br.com.gew.api.model.output.ProjetoConcluidosPorDiaOutputDTO;
import br.com.gew.api.model.output.ProjetoContagemOutputDTO;
import br.com.gew.api.model.output.ProjetoVerbaOutputDTO;
import br.com.gew.domain.entities.Projeto;
import br.com.gew.domain.entities.SecaoPagante;
import br.com.gew.domain.entities.StatusProjeto;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.ProjetosService;
import br.com.gew.domain.services.SecoesPagantesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProjetoContagemUtils {

    private ProjetosService projetosService;
    private SecoesPagantesService secoesPagantesService;

    public ContagemOutputDTO contar() throws ExceptionTratement {
        ContagemOutputDTO contagem = new ContagemOutputDTO();

        contagem.setContagem(contarPorStatus());
        contagem.setVerba(contarVerba());

        return contagem;
    }

    private ProjetoContagemOutputDTO contarPorStatus() throws ExceptionTratement {
        ProjetoContagemOutputDTO projetoContagem = new ProjetoContagemOutputDTO();

        projetoContagem.setConcluidos(projetosService.listarPorStatus(StatusProjeto.CONCLUIDO).size());
        projetoContagem.setAtrasados(projetosService.listarPorStatus(StatusProjeto.ATRASADO).size());
        projetoContagem.setEmAndamento(projetosService.listarPorStatus(StatusProjeto.EM_ANDAMENTO).size());
        projetoContagem.setTotal(projetosService.listar().size());

        return projetoContagem;
    }

    private ProjetoVerbaOutputDTO contarVerba() throws ExceptionTratement {
        ProjetoVerbaOutputDTO projetoVerba = new ProjetoVerbaOutputDTO();

        projetoVerba.setVerbaAtrasados(contarVerbaPorStatus(StatusProjeto.ATRASADO));
        projetoVerba.setVerbaConcluidos(contarVerbaPorStatus(StatusProjeto.CONCLUIDO));
        projetoVerba.setVerbaEmAndamento(contarVerbaPorStatus(StatusProjeto.EM_ANDAMENTO));
        projetoVerba.setVerbaTotal(contarVerbaTotal());

        return projetoVerba;
    }

    private double contarVerbaTotal() {
        double total = 0;
        List<Projeto> projetos = projetosService.listar();

        for (Projeto projeto : projetos) {
            List<SecaoPagante> secaoPagantes = secoesPagantesService.listarPorProjeto(projeto.getId());

            for (SecaoPagante secaoPagante : secaoPagantes) {
                total += secaoPagante.getValor();
            }
        }

        return total;
    }

    private double contarVerbaPorStatus(StatusProjeto statusProjeto) throws ExceptionTratement {
        double total = 0;
        List<Projeto> projetos = projetosService.listarPorStatus(statusProjeto);

        for (Projeto projeto : projetos) {
            List<SecaoPagante> secaoPagantes = secoesPagantesService.listarPorProjeto(projeto.getId());

            for (SecaoPagante secaoPagante : secaoPagantes) {
                total += secaoPagante.getValor();
            }
        }

        return total;
    }

    public List<ProjetoConcluidosPorDiaOutputDTO> concluidosUltimosDias() {
        LocalDate hoje = LocalDate.now();

        List<ProjetoConcluidosPorDiaOutputDTO> projetos = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            ProjetoConcluidosPorDiaOutputDTO projeto = new ProjetoConcluidosPorDiaOutputDTO();

            String data_nova = hoje.getDayOfMonth() + "/" + hoje.getMonth().getValue();

            projeto.setProjetosConcluidos(buscarProjetos(hoje).size());
            projeto.setData(data_nova);

            hoje = hoje.minusDays(1);

            projetos.add(projeto);
        }

        return projetos;
    }

    private List<Projeto> buscarProjetos(LocalDate data) throws ExceptionTratement {
        return projetosService.listarPorDataConclusao(data);
    }

}
