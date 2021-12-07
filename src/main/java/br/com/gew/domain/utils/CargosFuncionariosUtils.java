package br.com.gew.domain.utils;

import br.com.gew.domain.entities.CargoFuncionario;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.services.CargosFuncionariosService;
import br.com.gew.domain.services.CargosService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CargosFuncionariosUtils {

    private CargosFuncionariosService cargosFuncionariosService;
    private CargosService cargosService;

    public void cadastrar(String cargoNome, long funcionarioCracha) throws ExceptionTratement {
        CargoFuncionario cargoFuncionario = new CargoFuncionario();

        cargoFuncionario.setCargo_id(
                cargosService.buscarPorNome("ROLE_" + cargoNome.toUpperCase()).getId());
        cargoFuncionario.setFuncionario_cracha(funcionarioCracha);

        cargosFuncionariosService.cadastrar(cargoFuncionario);
    }

    public void editar(String cargoNome, long funcionarioCracha) throws ExceptionTratement {
        CargoFuncionario cargoFuncionario = new CargoFuncionario();

        cargoFuncionario.setCargo_id(
                cargosService.buscarPorNome("ROLE_" + cargoNome.toUpperCase()).getId());
        cargoFuncionario.setFuncionario_cracha(funcionarioCracha);

        cargosFuncionariosService.editar(cargoFuncionario);
    }

    public void remover(long funcionarioCracha) throws ExceptionTratement {
        if (cargosFuncionariosService.buscarPorFuncionario(funcionarioCracha) == null) {
            throw new ExceptionTratement("Funcionário não possui cargo");
        }

        long id = cargosFuncionariosService.buscarPorFuncionario(funcionarioCracha).getId();

        cargosFuncionariosService.remover(id);
    }

}
