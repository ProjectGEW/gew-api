package br.com.gew.domain.utils;

import br.com.gew.api.assembler.SecaoPaganteAssembler;
import br.com.gew.api.model.input.SecaoPaganteInputDTO;
import br.com.gew.api.model.output.SecaoPaganteOutputDTO;
import br.com.gew.domain.entities.SecaoPagante;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.ProjetosService;
import br.com.gew.domain.services.SecoesPagantesService;
import br.com.gew.domain.services.SecoesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SecoesPagantesUtils {

    private SecaoPaganteAssembler secaoPaganteAssembler;

    private SecoesService secoesService;
    private ProjetosService projetosService;
    private SecoesPagantesService secoesPagantesService;

    public void cadastrar(
            List<SecaoPaganteInputDTO> secaoPaganteInputDTOS,
            long numeroDoProjeto,
            double total
    ) throws ExceptionTratement {
        for (SecaoPaganteInputDTO secaoPaganteInputDTO : secaoPaganteInputDTOS) {
            SecaoPagante secaoPagante = secaoPaganteAssembler.toEntity(secaoPaganteInputDTO);

            secaoPagante.setSecao(secoesService.buscar(secaoPaganteInputDTO.getSecao_id()).get());
            secaoPagante.setPercentual(calcularPercentual(secaoPagante.getValor(), total));
            secaoPagante.setProjeto(projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get());

            secoesPagantesService.cadastrar(secaoPagante);
        }
    }

    private double calcularPercentual(double valor, double total) {
        return Math.floor((valor / total) * 100);
    }

    public List<SecaoPaganteOutputDTO> listar(long projetoId) throws ExceptionTratement {
        return secaoPaganteAssembler.toCollectionModel(
                secoesPagantesService.listarPorProjeto(projetoId)
        );
    }

    public void editar(
            List<SecaoPaganteInputDTO> secaoPaganteInputDTOS,
            long numeroDoProjeto,
            double total
    ) throws ExceptionTratement {
        List<SecaoPagante> secaoPagantes = secaoPaganteAssembler.toCollectionEntity(secaoPaganteInputDTOS);
        List<SecaoPagante> secaoPagantesDB = secoesPagantesService.listarPorProjeto(
                projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId()
        );

        for (int i = 0; i < secaoPagantesDB.size(); i ++) {
            secaoPagantes.get(i).setProjeto(projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get());
            secaoPagantes.get(i).setPercentual(calcularPercentual(secaoPagantes.get(i).getValor(), total));
            secaoPagantes.get(i).setSecao(
                    secoesService.buscar(secaoPaganteInputDTOS.get(i).getSecao_id()).get()
            );

            secoesPagantesService.editar(secaoPagantes.get(i), secaoPagantesDB.get(i).getId());
        }

        if (secaoPagantes.size() > secaoPagantesDB.size()) {
            for (int i = secaoPagantesDB.size(); i < secaoPagantes.size(); i ++) {
                secaoPagantes.get(i).setProjeto(projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get());
                secaoPagantes.get(i).setPercentual(
                        calcularPercentual(secaoPagantes.get(i).getValor(), total)
                );
                secaoPagantes.get(i).setSecao(
                        secoesService.buscar(secaoPaganteInputDTOS.get(i).getSecao_id()).get()
                );

                secoesPagantesService.cadastrar(secaoPagantes.get(i));
            }
        }
    }

    public void remover(long numeroDoProjeto) throws ExceptionTratement {
        List<SecaoPagante> secaoPagantesDB = secoesPagantesService.listarPorProjeto(
                projetosService.buscarPorNumeroProjeto(numeroDoProjeto).get().getId()
        );

        for (SecaoPagante secaoPagante : secaoPagantesDB) {
            secoesPagantesService.remover(secaoPagante.getId());
        }
    }
}
