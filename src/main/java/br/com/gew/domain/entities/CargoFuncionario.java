package br.com.gew.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cargos_funcionarios")
public class CargoFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long funcionario_cracha;

    private long cargo_id;

}
