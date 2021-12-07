package br.com.gew.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "alocados")
public class Alocado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long projeto_id;

    private long funcionario_cracha;

    private int horas_totais;

    private boolean status;

}
