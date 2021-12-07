package br.com.gew.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "consultores_fornecedores")
public class ConsultorFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long funcionario_cracha;

    private long fornecedor_id;

}
