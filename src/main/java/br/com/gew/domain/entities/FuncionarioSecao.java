package br.com.gew.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "funcionarios_secoes")
public class FuncionarioSecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long funcionario_cracha;

    private long secao_id;

}
