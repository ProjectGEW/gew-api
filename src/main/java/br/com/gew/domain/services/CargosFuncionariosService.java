package br.com.gew.domain.services;

import br.com.gew.domain.entities.CargoFuncionario;
import br.com.gew.domain.exception.EntityNotFoundException;
import br.com.gew.domain.exception.ExceptionTratement;
import br.com.gew.domain.repositories.CargosFuncionariosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CargosFuncionariosService {

    private CargosFuncionariosRepository cargosFuncionariosRepository;

    @Transactional
    public CargoFuncionario cadastrar(CargoFuncionario cargoFuncionario) throws ExceptionTratement {
        try {
            return cargosFuncionariosRepository.save(cargoFuncionario);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public CargoFuncionario buscarPorFuncionario(long numeroCracha) throws EntityNotFoundException {
        try {
            return cargosFuncionariosRepository.findByFuncionarioCracha(numeroCracha).get();
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public List<CargoFuncionario> listarPorCargo(long cargo_id) throws ExceptionTratement {
        try {
            return cargosFuncionariosRepository.findAllByCargoId(cargo_id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    @Transactional
    public CargoFuncionario editar(CargoFuncionario cargoFuncionario) throws ExceptionTratement {
        try {
            return cargosFuncionariosRepository.save(cargoFuncionario);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

    public void remover(long id) throws ExceptionTratement {
        try {
            cargosFuncionariosRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ExceptionTratement("Error: " + ex);
        }
    }

}
